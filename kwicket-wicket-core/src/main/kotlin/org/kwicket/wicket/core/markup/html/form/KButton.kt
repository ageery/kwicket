package org.kwicket.wicket.core.markup.html.form

import org.apache.wicket.WicketRuntimeException
import org.apache.wicket.markup.html.form.Button
import org.apache.wicket.model.IModel
import org.kwicket.NonAjaxHandler
import org.kwicket.component.init

/**
 * [Button] with named and default constructor arguments.
 */
open class KButton(id: String,
              model: IModel<String>? = null,
              defaultFormProcessing: Boolean? = null,
              val onSubmit: NonAjaxHandler,
              val onError: NonAjaxHandler? = null,
              outputMarkupId: Boolean? = null,
              outputMarkupPlaceholderTag: Boolean? = null)
    : Button(id, model) {

    init {
        init(outputMarkupId = outputMarkupId,
                outputMarkupPlaceholderTag = outputMarkupPlaceholderTag)
        this.defaultFormProcessing = defaultFormProcessing ?: this.defaultFormProcessing
    }

    override fun onSubmit() {
        onSubmit.invoke()
    }

    override fun onError() {
        onError?.invoke() ?: throw WicketRuntimeException("No onError handler defined for ${javaClass.name} with id=$id")
    }

}