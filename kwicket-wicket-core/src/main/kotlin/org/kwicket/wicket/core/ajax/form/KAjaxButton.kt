package org.kwicket.wicket.core.ajax.form

import org.apache.wicket.WicketRuntimeException
import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.ajax.markup.html.form.AjaxButton
import org.apache.wicket.behavior.Behavior
import org.apache.wicket.markup.html.form.Form
import org.apache.wicket.model.IModel
import org.kwicket.AjaxHandler
import org.kwicket.component.initComponent

class KAjaxButton(id: String,
                  model: IModel<String>? = null,
                  val onSubmit: AjaxHandler,
                  val onError: AjaxHandler? = null,
                  defaultFormProcessing: Boolean? = null,
                  form: Form<*>? = null,
                  outputMarkupId: Boolean? = null,
                  outputMarkupPlaceholderTag: Boolean? = null,
                  vararg behaviors: Behavior)
    : AjaxButton(id, model, form) {

    init {
        defaultFormProcessing?.let { this.defaultFormProcessing = it }
        initComponent(outputMarkupId = outputMarkupId,
                outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
                behaviors = *behaviors)
    }

    override fun onSubmit(target: AjaxRequestTarget) {
        onSubmit.invoke(target)
    }

    override fun onError(target: AjaxRequestTarget) {
        onError?.invoke(target) ?: throw WicketRuntimeException("No onError handler defined for ${javaClass.name} with id=$id")
    }

}