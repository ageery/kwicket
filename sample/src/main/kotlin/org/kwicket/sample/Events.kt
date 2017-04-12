package org.kwicket.sample

import org.apache.wicket.Component
import org.apache.wicket.ajax.AjaxRequestTarget

sealed class SampleAjaxEvent(val target: AjaxRequestTarget)
class AjaxResetEvent(target: AjaxRequestTarget) : SampleAjaxEvent(target)
class AjaxChangeEvent(target: AjaxRequestTarget, val successful: Boolean) : SampleAjaxEvent(target)

class NonAjaxResetEvent

val refreshOnAjaxEventHandler: (C: Component, SampleAjaxEvent) -> Unit = { c, e -> e.target.add(c) }
val refreshOnSuccessHandler: (C: Component, AjaxChangeEvent) -> Unit = { c, e -> if (e.successful) e.target.add(c) }
val refreshOnAjaxResetHandler: (C: Component, AjaxResetEvent) -> Unit = { c, e -> e.target.add(c) }

