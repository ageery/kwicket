package org.kwicket.sample

import org.apache.wicket.Page
import org.apache.wicket.protocol.http.WebApplication
import org.apache.wicket.protocol.http.WicketFilter
import org.kwicket.agilecoders.enableBootstrap
import org.kwicket.wicket.spring.enableSpringIoC
import org.kwicket.wicketstuff.annotation.enableMountAnnotations
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import javax.servlet.Filter


@SpringBootApplication
class SampleApp {
    @Bean fun getWebApp(): WebApplication = SampleWebApp()
    @Bean fun getWicketFilter(webApp: WebApplication): Filter = WicketFilter(webApp).apply { filterPath = "/" }
    @Bean fun loadData(repo: CustomerRepository): CommandLineRunner {
        return CommandLineRunner {
            sequenceOf(
                    Customer(firstName = "Jack", lastName = "Bauer"),
                    Customer(firstName = "Chloe", lastName = "O'Brien"),
                    Customer(firstName = "Kim", lastName = "Bauer"),
                    Customer(firstName = "David", lastName = "Palmer"),
                    Customer(firstName = "Michelle", lastName = "Dessler")
            ).forEach { repo.save(it) }
        }
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(SampleApp::class.java, *args)
}

class SampleWebApp : WebApplication() {
    override fun getHomePage(): Class<out Page> = HomePage::class.java
    override fun init() {
        super.init()
        enableBootstrap(deferJavascript = true)
        enableMountAnnotations(scanPackages = listOf("org.kwicket.sample"))
        enableSpringIoC()
    }
}

class HomePage : BasePage()