package org.kwicket.wicket.core.markup.html.form

import org.apache.wicket.behavior.Behavior
import org.apache.wicket.markup.html.form.TextField
import org.apache.wicket.model.IModel
import org.kwicket.component.init

/**
 * [TextField] with named and default constructor arguments.
 */
open class KTextField<T>(
    id: String,
    model: IModel<T?>? = null,
    type: Class<T>? = null,
    required: Boolean? = null,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    label: IModel<String>? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    behaviors: List<Behavior>? = null
) : TextField<T>(id, model, type) {

    init {
        init(
            outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
            outputMarkupId = outputMarkupId,
            visible = visible,
            enabled = enabled,
            behaviors = behaviors,
            required = required,
            label = label
        )
    }

}