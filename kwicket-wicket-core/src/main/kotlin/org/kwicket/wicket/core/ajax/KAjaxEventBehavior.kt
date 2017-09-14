package org.kwicket.wicket.core.ajax

import org.apache.wicket.ajax.AjaxEventBehavior
import org.apache.wicket.ajax.AjaxRequestTarget

/**
 * [AjaxEventBehavior] with named and default constructor arguments.
 */
open class KAjaxEventBehavior(event: String,
                              private val handler: (AjaxRequestTarget) -> Unit) : AjaxEventBehavior(event) {
    override fun onEvent(target: AjaxRequestTarget) = handler(target)
}
