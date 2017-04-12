package org.kwicket.behavior

import org.apache.wicket.Component
import org.apache.wicket.behavior.Behavior
import org.apache.wicket.event.IEvent

/**
 * Returns a [Behavior] where the [Behavior.onConfigure] functionality is provided by the lambda [handler].
 *
 * @param handler lambda that is executed in the [Behavior.onConfigure] method
 * @return [Behavior] with the [Behavior.onConfigure] specified by the lambda [handler]
 */
fun onConfigure(handler: (Component) -> Unit): Behavior {
    return object : Behavior() {
        override fun onConfigure(c: Component) = handler(c)
    }
}

/**
 * Returns a [Behavior] where the [Behavior.onEvent] functionality is provided by the lambda [handler]. The lambda
 * [handler] takes two parameters: the component the behavior is associated with and the event object.
 * The [handler] will only be invoked if the payload of the event is of type [E].
 *
 * @param handler lambda that is executed in the [Behavior.onEvent] method
 * @return [Behavior] with the [Behavior.onEvent] specified by the lambda [handler]
 */
inline fun <reified C: Component, reified E> eventHandler(crossinline handler: (C, E) -> Unit): Behavior {
    return object : Behavior() {
        override fun onEvent(component: Component, event: IEvent<*>) {
            val payload = event.payload
            if (payload is E) {
                handler(component as C, payload)
            }
        }
    }
}
