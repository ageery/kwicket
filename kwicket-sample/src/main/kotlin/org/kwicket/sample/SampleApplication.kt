package org.kwicket.sample

import de.agilecoders.wicket.core.markup.html.themes.bootstrap.BootstrapTheme
import de.agilecoders.wicket.core.settings.SingleThemeProvider
import org.apache.wicket.RuntimeConfigurationType
import org.kwicket.agilecoders.enableBootstrap
import org.kwicket.wicket.core.protocol.http.KWebApplication
import org.kwicket.wicket.core.protocol.http.KWicketFilter
import org.kwicket.wicket.spring.enableSpringIoC
import org.kwicket.wicketstuff.annotation.enableMountAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service
import java.util.*
import javax.servlet.Filter

@SpringBootApplication
class SampleApplication {

    @Value("\${wicket.config}")
    private lateinit var wicketConfig: RuntimeConfigurationType

    @Bean
    fun getWicketFilter(): Filter = KWicketFilter(webApp = SampleWebApplication(configurationType = wicketConfig), filterPath = "/")

    @Bean
    fun getCustomers(): MutableList<Customer> = mutableListOf(
            Customer(id = UUID.randomUUID(), firstName = "Alice", lastName = "Anderson", address = Address(city = "Ann Arbor", country = Country.US)),
            Customer(id = UUID.randomUUID(), firstName = "Bob", lastName = "Bo", address = Address(city = "Beijing", country = Country.China))
    )

}

class SampleWebApplication(configurationType: RuntimeConfigurationType = RuntimeConfigurationType.DEVELOPMENT)
    : KWebApplication(configurationType = configurationType) {

    override fun getHomePage() = ManageCustomersPage::class.java

    override fun init() {
        super.init()
        enableMountAnnotations(scanPackages = listOf("org.kwicket.sample"))
        enableBootstrap(themeProvider = SingleThemeProvider(BootstrapTheme()))
        enableSpringIoC()
    }

}

fun main(args: Array<String>) {
    SpringApplication.run(SampleApplication::class.java)
}

enum class CustomerSort {
    FirstName,
    LastName
}

interface CustomerService {
    fun find(term: String?, sort: CustomerSort? = null, first: Int, count: Int, asc: Boolean = true): Sequence<Customer>
    fun count(term: String?): Int
    fun insert(customer: Customer)
    fun update(customer: Customer)
    fun delete(customer: Customer)
}

@Service
class CustomerServiceImpl(@Autowired val customers: MutableList<Customer>) : CustomerService {

    val sortLambda = { sort: CustomerSort, customer: Customer -> if (sort == CustomerSort.FirstName) customer.firstName else customer.lastName }

    private val nameFilter: (String?, Customer) -> Boolean = { term, customer ->
        term
                ?.toLowerCase()
                ?.let { t ->
                    listOf(customer.lastName, customer.firstName)
                            .map { it.toLowerCase() }
                            .any { it.contains(other = t) }
                } ?: true
    }

    private fun filter(term: String?) = customers
            .asSequence()
            .filter { customer -> nameFilter(term, customer) }

    override fun find(term: String?, sort: CustomerSort?, first: Int, count: Int, asc: Boolean): Sequence<Customer> {
        var filtered = filter(term = term)
        sort?.let {
            filtered = if (asc) filtered.sortedBy { sortLambda(sort, it) } else filtered.sortedByDescending { sortLambda(sort, it) }
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
            address.city = customer.address.city
            address.country = customer.address.country
        }
    }

    override fun delete(customer: Customer) {
        customers.remove(customer)
    }

}