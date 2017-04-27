package org.kwicket.behavior

import org.apache.wicket.Component
import org.apache.wicket.behavior.Behavior
import org.apache.wicket.event.IEvent
import kotlin.reflect.KClass

// FIXME: remove this
class OnEventBehavior<T: Any>(val payloadClass: KClass<T>,
                         val handler: (Component, T) -> Unit,
                         val guard: ((Any) -> Boolean)?) : Behavior() {

    override fun onEvent(component: Component, event: IEvent<*>) {
        val payload = event.payload
        if (payload::class.java.isAssignableFrom(payloadClass.java)) {
            val guardCheck = guard?.invoke(payload) ?: true
            if (guardCheck) {
                @Suppress("UNCHECKED_CAST")
                handler(component, payload as T)
            }
        }
    }

}
