package org.kwicket.wicket.core.markup.html.form

import org.apache.wicket.behavior.Behavior
import org.apache.wicket.markup.html.form.Form
import org.apache.wicket.markup.html.form.validation.IFormValidator
import org.apache.wicket.model.IModel
import org.kwicket.component.init

/**
 * [Form] with named and default constructor arguments.
 *
 * @param T type of the model backing the component
 * @param id Wicket markup id
 * @param model model for the component
 * @param outputMarkupId whether to include an HTML id for the component in the markup
 * @param outputMarkupPlaceholderTag whether to include a placeholder tag for the component in the markup when the
 * component is not visible
 * @param visible whether the component is visible
 * @param enabled whether the component is enabled
 * @param escapeModelStrings whether model strings should be escaped
 * @param renderBodyOnly whether the tag associated with the component should be included in the markup
 * @param behaviors list of [Behavior] to add to the component
 * @param validators list of form validators to add to the form
 */
open class KForm<T>(
    id: String,
    model: IModel<T>? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    outputMarkupId: Boolean? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    escapeModelStrings: Boolean? = null,
    renderBodyOnly: Boolean? = null,
    validators: List<IFormValidator>? = null,
    behaviors: List<Behavior>? = null
) : Form<T>(id, model) {

    /**
     * Creates a new [KForm].
     *
     * @param id Wicket markup id
     * @param model model for the component
     * @param outputMarkupId whether to include an HTML id for the component in the markup
     * @param outputMarkupPlaceholderTag whether to include a placeholder tag for the component in the markup when the
     * component is not visible
     * @param visible whether the component is visible
     * @param enabled whether the component is enabled
     * @param escapeModelStrings whether model strings should be escaped
     * @param renderBodyOnly whether the tag associated with the component should be included in the markup
     * @param behavior [Behavior] to add to the component
     * @param validators list of form validators to add to the form
     */
    constructor(
        id: String,
        model: IModel<T>? = null,
        outputMarkupPlaceholderTag: Boolean? = null,
        outputMarkupId: Boolean? = null,
        visible: Boolean? = null,
        enabled: Boolean? = null,
        escapeModelStrings: Boolean? = null,
        renderBodyOnly: Boolean? = null,
        validators: List<IFormValidator>? = null,
        behavior: Behavior
    ) : this(
        id = id,
        model = model,
        outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
        outputMarkupId = outputMarkupId,
        visible = visible,
        enabled = enabled,
        escapeModelStrings = escapeModelStrings,
        renderBodyOnly = renderBodyOnly,
        behaviors = listOf(behavior),
        validators = validators
    )

    init {
        init(
            outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
            outputMarkupId = outputMarkupId,
            visible = visible,
            enabled = enabled,
            escapeModelStrings = escapeModelStrings,
            renderBodyOnly = renderBodyOnly,
            behaviors = behaviors
        )
        validators?.forEach { add(it) }
    }
}