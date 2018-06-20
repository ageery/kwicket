package org.kwicket.wicket.core.ajax.form

import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior
import org.apache.wicket.markup.html.form.Form
import org.kwicket.AjaxHandler

class KAjaxFormSubmitBehavior(
    event: String,
    form: Form<*>? = null,
    private val onSubmit: AjaxHandler? = null,
    private val onError: AjaxHandler? = null,
    private val updateAjaxAttributes: ((AjaxRequestAttributes) -> Unit)? = null
) : AjaxFormSubmitBehavior(form, event) {

    override fun onSubmit(target: AjaxRequestTarget) {
        onSubmit?.let { it(target) }
    }

    override fun onError(target: AjaxRequestTarget) {
        onError?.let { it(target) }
    }

    override fun updateAjaxAttributes(attributes: AjaxRequestAttributes) {
        super.updateAjaxAttributes(attributes)
        updateAjaxAttributes?.let { it(attributes) }
    }
}