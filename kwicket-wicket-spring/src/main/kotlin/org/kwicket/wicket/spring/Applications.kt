package org.kwicket.wicket.spring

import org.apache.wicket.protocol.http.WebApplication
import org.apache.wicket.spring.injection.annot.SpringComponentInjector

/**
 * Extension method to enable Spring IoC in Wicket.
 *
 * @receiver Wicket application
 * @return the same Wicket application as the @receiver
 */
fun <A : WebApplication> A.enableSpringIoC(): A {
    componentInstantiationListeners.add(SpringComponentInjector(this))
    return this
}
