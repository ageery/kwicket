package org.kwicket.sample

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

interface CustomerService {
    fun find(term: String?, sort: CustomerSort? = null, first: Int, count: Int, asc: Boolean = true): Sequence<Customer>
    fun count(term: String?): Int
    fun insert(customer: Customer)
    fun update(customer: Customer)
    fun delete(customer: Customer)
}

@Service
class CustomerServiceImpl(@Autowired val customers: MutableList<Customer>) : CustomerService {

    val sortLambda =
        { sort: CustomerSort, customer: Customer -> if (sort == CustomerSort.FirstName) customer.firstName else customer.lastName }

    private val nameFilter: (String?, Customer) -> Boolean = { term, customer ->
        term
            ?.toLowerCase()
            ?.let { t ->
                listOf(customer.lastName, customer.firstName)
                    .map { it.toLowerCase() }
                    .any { it.contains(other = t) }
            } != false
    }

    private fun filter(term: String?) = customers
        .asSequence()
        .filter { customer -> nameFilter(term, customer) }

    override fun find(term: String?, sort: CustomerSort?, first: Int, count: Int, asc: Boolean): Sequence<Customer> {
        var filtered = filter(term = term)
        sort?.let {
            filtered = if (asc) filtered.sortedBy { sortLambda(sort, it) } else filtered.sortedByDescending {
                sortLambda(
                    sort,
                    it
                )
            }
        }
        return filtered.drop(first)
            .take(count)
    }

    override fun count(term: String?) = filter(term = term).count()

    override fun insert(customer: Customer) {
        customers.add(customer)
    }

    override fun update(customer: Customer) {
        val x = customers.findLast { it.id == customer.id }
        with(x!!) {
            firstName = customer.firstName
            lastName = customer.lastName
            age = customer.age
            address.city = customer.address.city
            address.country = customer.address.country
        }
    }

    override fun delete(customer: Customer) {
        customers.remove(customer)
    }

}