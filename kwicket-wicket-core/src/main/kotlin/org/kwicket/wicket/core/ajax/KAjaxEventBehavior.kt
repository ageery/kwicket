package org.kwicket.wicket.core.ajax

import org.apache.wicket.ajax.AjaxEventBehavior
import org.apache.wicket.ajax.AjaxRequestTarget

class KAjaxEventBehavior(event: String, val handler: (AjaxRequestTarget) -> Unit) : AjaxEventBehavior(event) {

    override fun onEvent(target: AjaxRequestTarget) = handler(target)

}
