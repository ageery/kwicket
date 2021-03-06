package org.kwicket.wicket.core.ajax.markup.html

import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.ajax.markup.html.AjaxLink
import org.apache.wicket.model.IModel
import org.kwicket.AjaxClickHandler
import org.kwicket.component.init
import org.kwicket.model.value

class KAjaxLink<T>(
    id: String,
    model: IModel<T>? = null,
    private val onClick: AjaxClickHandler<T>,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null
) : AjaxLink<T>(id, model) {

    init {
        init(
            outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
            outputMarkupId = outputMarkupId
        )
    }

    override fun onClick(target: AjaxRequestTarget) {
        onClick(target, model?.value)
    }

}