package org.kwicket.wicket.spring

import org.apache.wicket.protocol.http.WebApplication
import org.apache.wicket.spring.injection.annot.SpringComponentInjector

fun <A : WebApplication> A.enableSpringIoC(): A {
    componentInstantiationListeners.add(SpringComponentInjector(this))
    return this
}
