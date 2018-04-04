package org.kwicket.wicket.core.ajax.form

import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.ajax.markup.html.form.AjaxButton
import org.apache.wicket.behavior.Behavior
import org.apache.wicket.markup.html.form.Form
import org.apache.wicket.model.IModel
import org.kwicket.AjaxHandler
import org.kwicket.component.init
import org.slf4j.LoggerFactory

/**
 * [AjaxButton] with named and default constructor arguments.
 */
open class KAjaxButton(
    id: String,
    model: IModel<String>? = null,
    form: Form<*>? = null,
    private val onSubmit: AjaxHandler,
    private val onError: AjaxHandler? = null,
    defaultFormProcessing: Boolean? = null,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    behaviors: List<Behavior>? = null
) : AjaxButton(id, model, form) {

    companion object {
        private val logger = LoggerFactory.getLogger(KAjaxButton::class.java)
    }

    init {
        defaultFormProcessing?.let { this.defaultFormProcessing = it }
        init(
            outputMarkupId = outputMarkupId,
            outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
            behaviors = behaviors
        )
    }

    override fun onSubmit(target: AjaxRequestTarget) {
        onSubmit.invoke(target)
    }

    override fun onError(target: AjaxRequestTarget) {
        onError?.invoke(target) ?: logger.warn(
            "The onError() method was invoked on component " +
                    "${javaClass.name} with id='$id' but no onError handler was defined."
        )
    }

}