package org.kwicket.application

import org.apache.wicket.Component
import org.apache.wicket.application.IComponentOnConfigureListener
import org.kwicket.behavior.asyncLoad
import org.kwicket.model.AsyncModel

/**
 * A [IComponentOnConfigureListener] that starts the load of [AsyncModel] in the onConfigure method.
 */
class AsyncModelLoaderOnConfigureListener : IComponentOnConfigureListener {
    override fun onConfigure(component: Component) = component.asyncLoad()
}