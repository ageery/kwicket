package org.kwicket.wicket.core.markup.html.form

import org.apache.wicket.behavior.Behavior
import org.apache.wicket.markup.html.form.ChoiceRenderer
import org.apache.wicket.markup.html.form.DropDownChoice
import org.apache.wicket.markup.html.form.IChoiceRenderer
import org.apache.wicket.model.IModel
import org.kwicket.component.init

/**
 * [DropDownChoice] with named and default constructor arguments.
 *
 * @param T type of the backing model
 * @param id Wicket markup id
 * @param choices list of choices in the drop-down
 * @param renderer how the choices are to be rendered
 * @param model model for the component
 * @param outputMarkupId whether to include an HTML id for the component in the markup
 * @param outputMarkupPlaceholderTag whether to include a placeholder tag for the component in the markup when the
 * component is not visible
 * @param visible whether the component is visible
 * @param enabled whether the component is enabled
 * @param escapeModelStrings whether model strings should be escaped
 * @param renderBodyOnly whether the tag associated with the component should be included in the markup
 * @param behaviors list of [Behavior] to add to the component
 * @param label label associated with the drop-down
 * @param nullValid whether a null should be included in the list of options
 * @param required whether a value is required for the field
 */
open class KDropDownChoice<T>(
    id: String,
    model: IModel<T>? = null,
    choices: List<T>,
    renderer: IChoiceRenderer<T> = ChoiceRenderer<T>(),
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

    /**
     * Creates a [KDropDownChoice] component with a single behavior.
     *
     * @param T type of the backing model
     * @param id Wicket markup id
     * @param choices list of choices in the drop-down
     * @param renderer how the choices are to be rendered
     * @param model model for the component
     * @param outputMarkupId whether to include an HTML id for the component in the markup
     * @param outputMarkupPlaceholderTag whether to include a placeholder tag for the component in the markup when the
     * component is not visible
     * @param visible whether the component is visible
     * @param enabled whether the component is enabled
     * @param escapeModelStrings whether model strings should be escaped
     * @param renderBodyOnly whether the tag associated with the component should be included in the markup
     * @param behavior [Behavior] to add to the component
     * @param label label associated with the drop-down
     * @param nullValid whether a null should be included in the list of options
     * @param required whether a value is required for the field
     */
    constructor(
        id: String,
        choices: List<T>,
        renderer: IChoiceRenderer<T> = ChoiceRenderer<T>(),
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
        behavior: Behavior
    ) : this(
        id = id,
        model = model,
        choices = choices,
        renderer = renderer,
        required = required,
        nullValid = nullValid,
        label = label,
        outputMarkupId = outputMarkupId,
        outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
        visible = visible,
        enabled = enabled,
        escapeModelStrings = escapeModelStrings,
        renderBodyOnly = renderBodyOnly,
        behaviors = listOf(behavior)
    )

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