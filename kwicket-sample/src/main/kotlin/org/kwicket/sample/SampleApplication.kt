package org.kwicket.sample

import de.agilecoders.wicket.core.markup.html.themes.bootstrap.BootstrapTheme
import de.agilecoders.wicket.core.settings.SingleThemeProvider
import org.apache.wicket.Component
import org.apache.wicket.RuntimeConfigurationType
import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.event.Broadcast
import org.apache.wicket.model.IModel
import org.apache.wicket.util.time.Duration
import org.kwicket.agilecoders.enableBootstrap
import org.kwicket.agilecoders.wicket.core.ajax.markup.html.bootstrap.common.KNotificationMessage
import org.kwicket.application.AsyncModelLoaderOnConfigureListener
import org.kwicket.component.target
import org.kwicket.model.model
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
    fun getWicketFilter(): Filter =
        KWicketFilter(webApp = SampleWebApplication(configurationType = wicketConfig), filterPath = "/")

    @Bean
    fun getCustomers(): MutableList<Customer> = mutableListOf(
        Customer(
            id = UUID.randomUUID(),
            firstName = "Alice",
            lastName = "Anderson",
            address = Address(city = "Ann Arbor", country = Country.US)
        ),
        Customer(
            id = UUID.randomUUID(),
            firstName = "Bob",
            lastName = "Bo",
            address = Address(city = "Beijing", country = Country.China)
        )
    )

}

class SampleWebApplication(configurationType: RuntimeConfigurationType) :
    KWebApplication(configurationType = configurationType) {

    override fun getHomePage() = ManageCustomersPage::class.java

    override fun init() {
        super.init()
        enableMountAnnotations(scanPackages = listOf("org.kwicket.sample"))
        enableBootstrap(themeProvider = SingleThemeProvider(BootstrapTheme()))
        enableSpringIoC()
        //componentOnConfigureListeners.add(AsyncModelLoaderOnConfigureListener())
    }

}

fun main(args: Array<String>) {
    SpringApplication.run(SampleApplication::class.java)
}

private val notificationDuration: Duration = Duration.seconds(4)

fun Component.success(msg: IModel<String>, target: AjaxRequestTarget? = null, vararg refresh: Component) {
    this.feedback(feedback = {
        success(
            KNotificationMessage(
                message = msg,
                header = "Success!".model(),
                hideAfter = notificationDuration
            )
        )
    }, target = target, refresh = *refresh)
}

fun Component.info(msg: IModel<String>, target: AjaxRequestTarget? = null, vararg refresh: Component) {
    this.feedback(feedback = {
        info(
            KNotificationMessage(
                message = msg,
                hideAfter = notificationDuration
            )
        )
    }, target = target, refresh = *refresh)
}

private fun Component.feedback(feedback: () -> Unit, target: AjaxRequestTarget? = null, vararg refresh: Component) {
    feedback()
    val t = target(target)
    if (refresh.isNotEmpty()) t.add(*refresh)
    send(this, Broadcast.BUBBLE, HasFeedbackEvent(t))
}