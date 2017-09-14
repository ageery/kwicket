package org.kwicket.behavior

import org.apache.wicket.Component
import org.apache.wicket.behavior.Behavior

open class OnConfigureBehavior(val handler: (Component) -> Unit) : Behavior() {

    override fun onConfigure(component: Component) {
        super.onConfigure(component)
        handler(component)
    }

}

class VisibleWhen(val isVisible: () -> Boolean) : OnConfigureBehavior(handler = { c -> c.isVisible = isVisible() })
class EnabledWhen(val isEnabled: () -> Boolean) : OnConfigureBehavior(handler = { c -> c.isEnabled = isEnabled() })

fun <C: Component> C.onConfig(handler: (C) -> Unit): C {
    add(object : Behavior() {
        override fun onConfigure(component: Component) {
            super.onConfigure(component)
            @Suppress("UNCHECKED_CAST")
            handler(component as C)
        }
    })
    return this
}