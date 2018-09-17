package org.kwicket.wicket.core.markup.html.basic

import org.apache.wicket.behavior.Behavior
import org.apache.wicket.markup.html.WebMarkupContainer
import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.model.IModel
import org.kwicket.component.init
import org.kwicket.component.q

@Deprecated(
    message = "Use the method in the lambda package",
    replaceWith = ReplaceWith(expression = "org.kwicket.wicket.core.lambda.label")
)
fun label(
    model: IModel<*>? = null,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    escapeModelStrings: Boolean? = null,
    renderBodyOnly: Boolean? = null,
    behaviors: List<Behavior>? = null
): (String) -> Label = {
    KLabel(
        id = it,
        model = model,
        outputMarkupId = outputMarkupId,
        outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
        visible = visible,
        enabled = enabled,
        escapeModelStrings = escapeModelStrings,
        renderBodyOnly = renderBodyOnly,
        behaviors = behaviors
    )
}

@Deprecated(
    message = "Use the method in the queued package",
    replaceWith = ReplaceWith(expression = "org.kwicket.wicket.core.queued.label")
)
fun WebMarkupContainer.label(
    id: String,
    model: IModel<*>? = null,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    escapeModelStrings: Boolean? = null,
    renderBodyOnly: Boolean? = null,
    behaviors: List<Behavior>? = null
) = q(
    KLabel(
        id = id,
        model = model,
        outputMarkupId = outputMarkupId,
        outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
        visible = visible,
        enabled = enabled,
        escapeModelStrings = escapeModelStrings,
        renderBodyOnly = renderBodyOnly,
        behaviors = behaviors
    )
)

@Deprecated(
    message = "Use the method in the queued package",
    replaceWith = ReplaceWith(expression = "org.kwicket.wicket.core.queued.label")
)
fun WebMarkupContainer.label(
    id: String,
    model: IModel<*>? = null,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    escapeModelStrings: Boolean? = null,
    renderBodyOnly: Boolean? = null,
    behavior: Behavior
) = q(
    KLabel(
        id = id,
        model = model,
        outputMarkupId = outputMarkupId,
        outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
        visible = visible,
        enabled = enabled,
        escapeModelStrings = escapeModelStrings,
        renderBodyOnly = renderBodyOnly,
        behavior = behavior
    )
)

/**
 * [Label] with named and default constructor arguments.
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
 * @param behaviors list of [Behavior] to add to the component
 */
open class KLabel(
    id: String,
    model: IModel<*>? = null, // FIXME: make the model required!
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    escapeModelStrings: Boolean? = null,
    renderBodyOnly: Boolean? = null,
    behaviors: List<Behavior>? = null
) : Label(id, model) {

    /**
     * Creates a [KLabel] instance with a single [Behavior].
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
     */
    constructor(
        id: String,
        model: IModel<*>? = null,
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
            behaviors = behaviors
        )
    }

}