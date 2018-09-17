package org.kwicket.wicket.core.markup.html.image

import org.apache.wicket.behavior.Behavior
import org.apache.wicket.markup.html.image.Picture
import org.apache.wicket.model.IModel
import org.kwicket.component.init

open class KPicture(
    id: String,
    model: IModel<*>? = null,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    renderBodyOnly: Boolean? = null,
    escapeModelStrings: Boolean? = null,
    behaviors: List<Behavior>? = null
) : Picture(id, model) {

    constructor(
        id: String,
        model: IModel<*>? = null,
        outputMarkupId: Boolean? = null,
        outputMarkupPlaceholderTag: Boolean? = null,
        visible: Boolean? = null,
        enabled: Boolean? = null,
        renderBodyOnly: Boolean? = null,
        escapeModelStrings: Boolean? = null,
        behavior: Behavior
    ) : this(
        id = id,
        model = model,
        outputMarkupId = outputMarkupId,
        outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
        visible = visible,
        enabled = enabled,
        renderBodyOnly = renderBodyOnly,
        escapeModelStrings = escapeModelStrings,
        behaviors = listOf(behavior)
    )

    init {
        init(
            outputMarkupId = outputMarkupId,
            outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
            renderBodyOnly = renderBodyOnly,
            escapeModelStrings = escapeModelStrings,
            visible = visible,
            enabled = enabled,
            behaviors = behaviors
        )
    }

}