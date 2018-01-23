package org.kwicket.wicket.spring

import org.apache.wicket.protocol.http.WebApplication
import org.apache.wicket.spring.injection.annot.SpringComponentInjector
import org.springframework.web.context.support.WebApplicationContextUtils

fun <A : WebApplication> A.enableSpringIoC(): A {
    componentInstantiationListeners.add(
        SpringComponentInjector(
            this,
            WebApplicationContextUtils.getWebApplicationContext(servletContext)
        )
    )
    return this
}
