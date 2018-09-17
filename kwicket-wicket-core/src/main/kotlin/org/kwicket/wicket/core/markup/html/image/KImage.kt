package org.kwicket.wicket.core.markup.html.image

import org.apache.wicket.behavior.Behavior
import org.apache.wicket.markup.html.image.Image
import org.apache.wicket.model.IModel
import org.apache.wicket.request.mapper.parameter.PageParameters
import org.apache.wicket.request.resource.IResource
import org.apache.wicket.request.resource.ResourceReference
import org.kwicket.component.init

// FIXME: make a constructor that just takes a single imageResource
open class KImage(
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
    behaviors: List<Behavior>? = null
) : Image(id, model) {

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

        resRef?.let { setImageResourceReference(it, resParams) }
        setImageResourceReferences(resParams, *resRefs?.toTypedArray() ?: emptyArray())
        imageResources?.let {
            if (it.isNotEmpty()) {
                imageResource = it[0]
                if (it.size > 1) {
                    setImageResources(*it.slice(1..it.size).toTypedArray())
                }
            }
        }
        xValues?.let { setXValues(*it.toTypedArray()) }
        sizes?.let { setSizes(*sizes.toTypedArray()) }
    }

}