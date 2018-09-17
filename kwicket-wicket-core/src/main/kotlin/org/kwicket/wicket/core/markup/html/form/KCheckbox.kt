package org.kwicket.wicket.core.markup.html.form

import org.apache.wicket.behavior.Behavior
import org.apache.wicket.markup.html.form.CheckBox
import org.apache.wicket.model.IModel
import org.kwicket.component.init

/**
 * [Checkbox] with named and default constructor arguments.
 *
 * @param id Wicket markup id
 * @param model model for the component
 * @param outputMarkupId whether to include an HTML id for the component in the markup
 * @param outputMarkupPlaceholderTag whether to include a placeholder tag for the component in the markup when the
 * component is not visible
 * @param label model of the label associated with the field
 * @param visible whether the component is visible
 * @param enabled whether the component is enabled
 * @param renderBodyOnly whether the tag associated with the component should be included in the markup
 * @param behaviors list of [Behavior] to add to the component
 */
open class KCheckbox(
    id: String,
    model: IModel<Boolean>,
    outputMarkupPlaceholderTag: Boolean? = null,
    outputMarkupId: Boolean? = null,
    label: IModel<String>? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    renderBodyOnly: Boolean? = null,
    behaviors: List<Behavior>? = null
) : CheckBox(id, model) {

    constructor(
        id: String,
        model: IModel<Boolean>,
        outputMarkupPlaceholderTag: Boolean? = null,
        outputMarkupId: Boolean? = null,
        label: IModel<String>? = null,
        visible: Boolean? = null,
        enabled: Boolean? = null,
        renderBodyOnly: Boolean? = null,
        behavior: Behavior
    ) : this(
        id = id,
        model = model,
        outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
        outputMarkupId = outputMarkupId,
        label = label,
        visible = visible,
        enabled = enabled,
        renderBodyOnly = renderBodyOnly,
        behaviors = listOf(behavior)
    )

    init {
        init(
            outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
            outputMarkupId = outputMarkupId,
            behaviors = behaviors,
            label = label,
            visible = visible,
            enabled = enabled,
            renderBodyOnly = renderBodyOnly
        )
    }
}