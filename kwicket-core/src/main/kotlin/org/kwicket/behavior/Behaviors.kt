package org.kwicket.behavior

import org.apache.wicket.Component
import org.apache.wicket.behavior.Behavior
import org.kwicket.model.AsyncModel

open class OnConfigureBehavior(val handler: (Component) -> Unit) : Behavior() {

    override fun onConfigure(component: Component) {
        super.onConfigure(component)
        handler(component)
    }

}

class VisibleWhen(isVisible: () -> Boolean) : OnConfigureBehavior(handler = { c -> c.isVisible = isVisible() })
class EnabledWhen(isEnabled: () -> Boolean) : OnConfigureBehavior(handler = { c -> c.isEnabled = isEnabled() })

fun <C : Component> C.onConfig(handler: (C) -> Unit): C {
    add(object : Behavior() {
        override fun onConfigure(component: Component) {
            super.onConfigure(component)
            @Suppress("UNCHECKED_CAST")
            handler(component as C)
        }
    })
    return this
}

fun Component.asyncLoad() {
    defaultModel.let { model ->
        if (model is AsyncModel<*>) {
            model.loadAsync()
        }
    }
}

class AsyncModelLoadBehavior() : Behavior() {

    override fun onConfigure(component: Component) {
        super.onConfigure(component)
        component.asyncLoad()
    }

}