package org.kwicket.application

import kotlinx.coroutines.experimental.DefaultDispatcher
import org.apache.wicket.Component
import org.apache.wicket.application.IComponentOnConfigureListener
import org.kwicket.behavior.asyncLoad
import kotlin.coroutines.experimental.CoroutineContext

class AsyncModelLoaderOnConfigureListener(val contexts: (Component) -> CoroutineContext = { DefaultDispatcher }) : IComponentOnConfigureListener {

    override fun onConfigure(component: Component) = component.asyncLoad(context = contexts(component))

}