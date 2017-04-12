package org.kwicket.sample

import de.agilecoders.wicket.core.Bootstrap
import org.apache.wicket.Page
import org.apache.wicket.protocol.http.WebApplication
import org.apache.wicket.protocol.http.WicketFilter
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.wicketstuff.annotation.scan.AnnotatedMountScanner
import javax.servlet.Filter



@SpringBootApplication
class SampleApp {
    @Bean fun getWebApp(): WebApplication = SampleWebApp()
    @Bean fun getWicketFilter(webApp: WebApplication): Filter = WicketFilter(webApp).apply { filterPath = "/" }
}

fun main(args: Array<String>) {
    SpringApplication.run(SampleApp::class.java, *args)
}

class SampleWebApp : WebApplication() {
    override fun getHomePage(): Class<out Page> = HomePage::class.java
    override fun init() {
        super.init()
        Bootstrap.install(this)
        AnnotatedMountScanner().scanPackage("org.kwicket.sample").mount(this)
    }
}

class HomePage : BasePage()