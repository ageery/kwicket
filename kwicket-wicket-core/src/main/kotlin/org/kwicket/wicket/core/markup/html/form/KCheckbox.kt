package org.kwicket.wicket.core.markup.html.form

import org.apache.wicket.behavior.Behavior
import org.apache.wicket.markup.html.form.CheckBox
import org.apache.wicket.model.IModel
import org.kwicket.component.init

/**
 * [Checkbox] with named and default constructor arguments.
 */
open class KCheckbox(
    id: String,
    model: IModel<Boolean>,
    outputMarkupPlaceholderTag: Boolean? = null,
    outputMarkupId: Boolean? = null,
    label: IModel<String>? = null,
    behaviors: List<Behavior>? = null
) : CheckBox(id, model) {

    init {
        init(
            outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
            outputMarkupId = outputMarkupId,
            behaviors = behaviors,
            label = label
        )
    }
}