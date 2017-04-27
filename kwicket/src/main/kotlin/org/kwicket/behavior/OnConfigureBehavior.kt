package org.kwicket.behavior

import org.apache.wicket.Component
import org.apache.wicket.behavior.Behavior

open class OnConfigureBehavior(val handler: (Component) -> Unit) : Behavior() {

    override fun onConfigure(component: Component) {
        super.onConfigure(component)
        handler(component)
    }

}
