package org.kwicket.wicket.core.markup.html.link

import org.apache.wicket.WicketRuntimeException
import org.apache.wicket.behavior.Behavior
import org.apache.wicket.markup.html.link.Link
import org.apache.wicket.model.IModel
import org.kwicket.NonAjaxHandler
import org.kwicket.component.init

/**
 * [Link] with named and default constructor arguments.
 */
open class KLink<T>(id: String,
                    model: IModel<T>? = null,
                    val onClick: NonAjaxHandler? = null,
                    outputMarkupId: Boolean? = null,
                    outputMarkupPlaceholderTag: Boolean? = null,
                    visible: Boolean? = null,
                    enabled: Boolean? = null,
                    renderBodyOnly: Boolean? = null,
                    escapeModelStrings: Boolean? = null,
                    vararg behaviors: Behavior)
    : Link<T>(id, model) {

    init {
        init(outputMarkupId = outputMarkupId,
                outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
                visible = visible,
                enabled = enabled,
                escapeModelStrings = escapeModelStrings,
                renderBodyOnly = renderBodyOnly,
                behaviors = *behaviors)
    }

    override fun onClick() = onClick?.invoke() ?: throw WicketRuntimeException("No onClick handler defined for ${javaClass.name} with id=$id")

}