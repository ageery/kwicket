package org.kwicket.component

import org.apache.wicket.Component
import org.apache.wicket.MarkupContainer
import org.apache.wicket.behavior.Behavior
import org.apache.wicket.markup.html.form.FormComponent
import org.apache.wicket.model.IModel
import org.kwicket.behavior.eventHandler

/**
 * Queues the [component] in the receiver and returns the [component].
 *
 * @receiver [MarkupContainer] to add [component] to
 * @return the [component] added the receiver
 */
fun <C : Component, M : MarkupContainer> M.q(component: C): C {
    queue(component)
    return component
}

fun <C : Component> C.initComponent(outputMarkupId: Boolean? = null,
                                    outputMarkupPlaceholderTag: Boolean? = null,
                                    vararg behaviors: Behavior): C {
    outputMarkupId?.let { this.outputMarkupId = it }
    outputMarkupPlaceholderTag?.let { this.outputMarkupPlaceholderTag = it }
    if (behaviors.isNotEmpty()) {
        add(*behaviors)
    }
    return this
}

fun <C : FormComponent<*>> C.initFormComponent(required: Boolean? = null,
                                                label: IModel<String>? = null): C {
    required?.let { this.isRequired = it }
    label?.let { this.label = it }
    return this
}

inline fun <reified C : Component, reified E> C.onEvent(crossinline handler: (C, E) -> Unit, guard: ((E) -> Boolean)? = null, stop: Boolean = false): C {
    add(eventHandler(handler = handler, guard = guard, stop = stop))
    return this
}