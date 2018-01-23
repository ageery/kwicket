package org.kwicket.wicket.core.ajax.form

import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior
import org.kwicket.AjaxHandler

/**
 * [AjaxFormComponentUpdatingBehavior] with named and default constructor arguments.
 */
open class KAjaxFormComponentUpdatingBehavior(
    event: String,
    private val onUpdate: AjaxHandler? = null,
    private val onError: AjaxHandler? = null,
    private val updateAjaxAttributes: ((AjaxRequestAttributes) -> Unit)? = null
) : AjaxFormComponentUpdatingBehavior(event) {

    override fun onUpdate(target: AjaxRequestTarget) {
        onUpdate?.let { it(target) }
    }

    override fun onError(target: AjaxRequestTarget, ex: RuntimeException) {
        onError?.let { it(target) }
    }

    override fun updateAjaxAttributes(attributes: AjaxRequestAttributes) {
        super.updateAjaxAttributes(attributes)
        updateAjaxAttributes?.let { it(attributes) }
    }
}