package org.kwicket.sample

import de.agilecoders.wicket.core.markup.html.themes.bootstrap.BootstrapTheme
import de.agilecoders.wicket.core.settings.SingleThemeProvider
import org.apache.wicket.RuntimeConfigurationType
import org.kwicket.agilecoders.enableBootstrap
import org.kwicket.wicket.core.protocol.http.KWebApplication
import org.kwicket.wicket.core.protocol.http.KWicketFilter
import org.kwicket.wicket.spring.enableSpringIoC
import org.kwicket.wicketstuff.annotation.enableMountAnnotations
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
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

class SampleWebApplication(configurationType: RuntimeConfigurationType): KWebApplication(configurationType = configurationType) {

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
