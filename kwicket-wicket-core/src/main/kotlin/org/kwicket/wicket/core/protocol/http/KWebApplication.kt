package org.kwicket.wicket.core.protocol.http

import org.apache.wicket.Page
import org.apache.wicket.RuntimeConfigurationType
import org.apache.wicket.WicketRuntimeException
import org.apache.wicket.protocol.http.WebApplication
import kotlin.reflect.KClass

/**
 * [WebApplication] with named parameters.
 *
 * @param configurationType optional Wicket configuration type
 */
abstract class KWebApplication(
    configurationType: RuntimeConfigurationType? = null,
    val homePage: KClass<out Page>? = null
) : WebApplication() {

    init {
        configurationType?.let { this.configurationType = it }
    }

    open override fun getHomePage(): Class<out Page> = homePage?.java ?:
            throw WicketRuntimeException("Home page not specified")

}

//operator fun Application.get(name: String)