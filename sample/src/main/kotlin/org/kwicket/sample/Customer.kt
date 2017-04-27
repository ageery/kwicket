package org.kwicket.sample

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

interface CustomerRepository : JpaRepository<Customer, Int> {
    fun countByLastNameStartsWithIgnoreCase(lastName: String): Long
    fun findByLastNameStartsWithIgnoreCase(lastName: String, pageable: Pageable): Page<Customer>
}

enum class CustomerSort(vararg val props: String) {
    ID("id"),
    FIRST_NAME("firstName", "lastName", "id"),
    LAST_NAME("lastName", "firstName", "id");
}

@Entity
class Customer(@Id @GeneratedValue  var id: Int? = null,
               var firstName: String? = null,
               var lastName: String? = null) : Serializable

