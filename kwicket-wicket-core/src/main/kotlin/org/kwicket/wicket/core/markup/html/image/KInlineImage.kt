package org.kwicket.wicket.core.markup.html.image

import org.apache.wicket.behavior.Behavior
import org.apache.wicket.markup.html.image.InlineImage
import org.apache.wicket.model.IModel
import org.apache.wicket.request.resource.PackageResourceReference
import org.kwicket.component.init

open class KInlineImage(
    id: String,
    model: IModel<*>? = null,
    resRef: PackageResourceReference,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    renderBodyOnly: Boolean? = null,
    escapeModelStrings: Boolean? = null,
    behaviors: List<Behavior>? = null
) : InlineImage(id, model, resRef) {

    constructor(
        id: String,
        model: IModel<*>? = null,
        resRef: PackageResourceReference,
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
        resRef = resRef,
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
            visible = visible,
            enabled = enabled,
            renderBodyOnly = renderBodyOnly,
            escapeModelStrings = escapeModelStrings,
            behaviors = behaviors
        )

    }

}