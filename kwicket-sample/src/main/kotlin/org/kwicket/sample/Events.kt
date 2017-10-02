package org.kwicket.sample

import org.apache.wicket.ajax.AjaxRequestTarget

data class CancelEvent(val target: AjaxRequestTarget)
data class SaveEvent<out T>(val target: AjaxRequestTarget, val content: T)
data class DeleteEvent<out T>(val target: AjaxRequestTarget, val content: T)
data class HasFeedbackEvent(val target: AjaxRequestTarget)