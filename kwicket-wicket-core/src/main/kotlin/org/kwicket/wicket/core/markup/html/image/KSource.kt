package org.kwicket.wicket.core.markup.html.image

import org.apache.wicket.behavior.Behavior
import org.apache.wicket.markup.html.image.Source
import org.apache.wicket.model.IModel
import org.apache.wicket.request.mapper.parameter.PageParameters
import org.apache.wicket.request.resource.IResource
import org.apache.wicket.request.resource.ResourceReference

open class KSource(
    id: String,
    model: IModel<*>? = null,
    resRef: ResourceReference? = null,
    resParams: PageParameters? = null,
    resRefs: List<ResourceReference>? = null,
    imageResources: List<IResource>? = null,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    renderBodyOnly: Boolean? = null,
    escapeModelStrings: Boolean? = null,
    xValues: List<String>? = null,
    sizes: List<String>? = null,
    media: String? = null,
    behaviors: List<Behavior>? = null
) : Source(id, model) {

    constructor(
        id: String,
        model: IModel<*>? = null,
        resRef: ResourceReference? = null,
        resParams: PageParameters? = null,
        resRefs: List<ResourceReference>? = null,
        imageResources: List<IResource>? = null,
        outputMarkupId: Boolean? = null,
        outputMarkupPlaceholderTag: Boolean? = null,
        visible: Boolean? = null,
        enabled: Boolean? = null,
        renderBodyOnly: Boolean? = null,
        escapeModelStrings: Boolean? = null,
        xValues: List<String>? = null,
        sizes: List<String>? = null,
        media: String? = null,
        behavior: Behavior
    ) : this(
        id = id,
        model = model,
        resRef = resRef,
        resParams = resParams,
        resRefs = resRefs,
        imageResources = imageResources,
        outputMarkupId = outputMarkupId,
        outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
        visible = visible,
        enabled = enabled,
        renderBodyOnly = renderBodyOnly,
        escapeModelStrings = escapeModelStrings,
        xValues = xValues,
        sizes = sizes,
        media = media,
        behaviors = listOf(behavior)
    )

    init {
        init(
            resRef = resRef,
            resParams = resParams,
            resRefs = resRefs,
            imageResources = imageResources,
            outputMarkupId = outputMarkupId,
            outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
            visible = visible,
            enabled = enabled,
            renderBodyOnly = renderBodyOnly,
            escapeModelStrings = escapeModelStrings,
            xValues = xValues,
            sizes = sizes,
            behaviors = behaviors
        )
        setMedia(media)
    }

}