package org.kwicket.wicket.core.markup.html.link

import org.apache.wicket.WicketRuntimeException
import org.apache.wicket.markup.html.link.Link
import org.apache.wicket.model.IModel
import org.kwicket.NonAjaxHandler

class KLink<T>(id: String, model: IModel<T>? = null, val onClick: NonAjaxHandler? = null) : Link<T>(id, model) {

    override fun onClick() {
        onClick?.invoke() ?: throw WicketRuntimeException("No onClick handler defined for ${javaClass.name} with id=$id")
    }
}