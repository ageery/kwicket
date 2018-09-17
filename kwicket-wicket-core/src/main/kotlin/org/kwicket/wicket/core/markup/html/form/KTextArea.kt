package org.kwicket.wicket.core.markup.html.form

import org.apache.wicket.behavior.Behavior
import org.apache.wicket.markup.html.form.TextArea
import org.apache.wicket.model.IModel
import org.kwicket.component.init

/**
 * [TextArea] with named and default constructor arguments.
 *
 * @param T type of the model backing the component
 * @param id Wicket markup id
 * @param model model for the component
 * @param required whether a value is required for the field
 * @param outputMarkupId whether to include an HTML id for the component in the markup
 * @param outputMarkupPlaceholderTag whether to include a placeholder tag for the component in the markup when the
 * component is not visible
 * @param label model of the label associated with the field
 * @param visible whether the component is visible
 * @param enabled whether the component is enabled
 * @param escapeModelStrings whether model strings should be escaped
 * @param renderBodyOnly whether the tag associated with the component should be included in the markup
 * @param behaviors list of [Behavior] to add to the component
 */
open class KTextArea<T>(
    id: String,
    model: IModel<T?>,
    required: Boolean? = null,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    label: IModel<String>? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    escapeModelStrings: Boolean? = null,
    renderBodyOnly: Boolean? = null,
    behaviors: List<Behavior>? = null
) : TextArea<T>(id, model) {

    /**
     * [TextArea] with named and default constructor arguments.
     *
     * @param id Wicket markup id
     * @param model model for the component
     * @param required whether a value is required for the field
     * @param outputMarkupId whether to include an HTML id for the component in the markup
     * @param outputMarkupPlaceholderTag whether to include a placeholder tag for the component in the markup when the
     * component is not visible
     * @param label model of the label associated with the field
     * @param visible whether the component is visible
     * @param enabled whether the component is enabled
     * @param escapeModelStrings whether model strings should be escaped
     * @param renderBodyOnly whether the tag associated with the component should be included in the markup
     * @param behavior [Behavior] to add to the component
     */
    constructor(
        id: String,
        model: IModel<T?>,
        required: Boolean? = null,
        outputMarkupId: Boolean? = null,
        outputMarkupPlaceholderTag: Boolean? = null,
        label: IModel<String>? = null,
        visible: Boolean? = null,
        enabled: Boolean? = null,
        escapeModelStrings: Boolean? = null,
        renderBodyOnly: Boolean? = null,
        behavior: Behavior
    ) : this(
        id = id,
        model = model,
        required = required,
        outputMarkupId = outputMarkupId,
        outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
        label = label,
        visible = visible,
        enabled = enabled,
        escapeModelStrings = escapeModelStrings,
        renderBodyOnly = renderBodyOnly,
        behaviors = listOf(behavior)
    )

    init {
        init(
            outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
            outputMarkupId = outputMarkupId,
            behaviors = behaviors,
            required = required,
            label = label,
            visible = visible,
            enabled = enabled,
            escapeModelStrings = escapeModelStrings,
            renderBodyOnly = renderBodyOnly
        )
    }

}