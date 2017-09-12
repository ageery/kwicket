package org.kwicket.sample

import java.io.Serializable
import java.util.*

enum class Country(val displayName: String) {
    US(displayName = "United States"),
    China(displayName = "People's Republic of China")
}

data class Address(var city: String, var country: Country) : Serializable {
    companion object {
        val maxCityLength: Int = 20
    }
}

data class Customer(val id: UUID, var firstName: String, var lastName: String, val address: Address) : Serializable {

    companion object {
        val maxFirstNameLength: Int = 15
        val maxLastNameLength: Int = 25
    }

}

data class EditCustomer(val id: UUID? = null,
                        var firstName: String? = null,
                        var lastName: String? = null,
                        var city: String? = null,
                        var country: Country? = null) : Serializable

val Customer.toEdit: EditCustomer
    get() = EditCustomer(id = this.id,
            firstName = this.firstName,
            lastName = this.lastName,
            city = this.address.city,
            country = this.address.country)

val EditCustomer.fromEdit: Customer
    get() = Customer(id = this.id ?: UUID.randomUUID(),
            firstName = this.firstName ?: throw RuntimeException("First name is required"),
            lastName = this.lastName ?: throw RuntimeException("Last name is required"),
            address = Address(city = this.city ?: throw RuntimeException("Address city is required"),
                    country = this.country ?: throw RuntimeException("Address country is required")))