package org.kwicket.component

import org.apache.wicket.Component
import org.apache.wicket.MarkupContainer
import org.apache.wicket.behavior.Behavior
import org.apache.wicket.markup.html.form.FormComponent
import org.apache.wicket.model.IModel

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
                                    visible: Boolean? = null,
                                    enabled: Boolean? = null,
                                    escapeModelStrings: Boolean? = null,
                                    vararg behaviors: Behavior): C {
    outputMarkupId?.let { this.outputMarkupId = it }
    outputMarkupPlaceholderTag?.let { this.outputMarkupPlaceholderTag = it }
    visible?.let { this.isVisible = it }
    enabled?.let { this.isEnabled = it }
    escapeModelStrings?.let { this.escapeModelStrings = it }
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
