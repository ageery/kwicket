package org.kwicket.agilecoders.wicket.core.ajax.markup.html.bootstrap.button

import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapAjaxButton
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons
import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType
import org.apache.wicket.WicketRuntimeException
import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.markup.html.form.Form
import org.apache.wicket.model.IModel
import org.kwicket.AjaxHandler
import org.kwicket.component.initComponent

class KBootstrapAjaxButton(id: String,
                           model: IModel<String>? = null,
                           val onSubmit: AjaxHandler,
                           defaultFormProcessing: Boolean? = null,
                           form: Form<*>? = null,
                           val onError: AjaxHandler? = null,
                           outputMarkupId: Boolean? = null,
                           outputMarkupPlaceholderTag: Boolean? = null,
                           type: Buttons.Type? = null,
                           icon: IconType? = null,
                           size: Buttons.Size? = null)
    : BootstrapAjaxButton(id, model, form, type) {

    init {
        initComponent(outputMarkupPlaceholderTag = outputMarkupPlaceholderTag, outputMarkupId = outputMarkupId)
        defaultFormProcessing?.let { this.defaultFormProcessing = it }
        icon?.let { setIconType(it) }
        size?.let { setSize(it) }
    }

    override fun onSubmit(target: AjaxRequestTarget) {
        onSubmit.invoke(target)
    }

    override fun onError(target: AjaxRequestTarget) {
        onError?.invoke(target) ?: throw WicketRuntimeException("No onError handler defined for ${javaClass.name} with id=$id")
    }

}