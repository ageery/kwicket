package org.kwicket.application

import org.apache.wicket.Component
import org.apache.wicket.application.IComponentOnConfigureListener
import org.kwicket.behavior.asyncLoad

class AsyncModelLoaderOnConfigureListener : IComponentOnConfigureListener {

    override fun onConfigure(component: Component) = component.asyncLoad()

}