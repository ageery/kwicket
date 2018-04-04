package org.kwicket.wicket.core.markup.html.form

import org.apache.wicket.behavior.Behavior
import org.apache.wicket.markup.html.form.DropDownChoice
import org.apache.wicket.markup.html.form.Form
import org.apache.wicket.markup.html.form.IChoiceRenderer
import org.apache.wicket.model.IModel
import org.kwicket.component.init

/**
 * [DropDownChoice] with named and default constructor arguments.
 */
open class KDropDownChoice<T>(
    id: String,
    choices: List<T>,
    renderer: IChoiceRenderer<T>,
    model: IModel<T>? = null,
    required: Boolean? = null,
    nullValid: Boolean? = null,
    label: IModel<String>? = null,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    escapeModelStrings: Boolean? = null,
    renderBodyOnly: Boolean? = null,
    behaviors: List<Behavior>? = null
) : DropDownChoice<T>(id, model, choices, renderer) {

    init {
        init(
            outputMarkupId = outputMarkupId,
            outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
            visible = visible,
            enabled = enabled,
            escapeModelStrings = escapeModelStrings,
            renderBodyOnly = renderBodyOnly,
            behaviors = behaviors,
            required = required,
            label = label
        )
        nullValid?.let { setNullValid(it) }
    }

}