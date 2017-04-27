package org.kwicket.sample

import org.apache.wicket.ajax.AjaxRequestTarget
import org.wicketstuff.event.annotation.AbstractAjaxAwareEvent
import org.wicketstuff.event.annotation.AbstractPayloadTypedEvent

class ValidationErrorEvent(target: AjaxRequestTarget) : AbstractAjaxAwareEvent(target)
class CancelEvent(target: AjaxRequestTarget) : AbstractAjaxAwareEvent(target)
class SaveEvent<T>(target: AjaxRequestTarget, payload: T) : AbstractPayloadTypedEvent<T>(target, payload)
class DeleteEvent<T>(target: AjaxRequestTarget, payload: T) : AbstractPayloadTypedEvent<T>(target, payload)
class EditEvent<T>(target: AjaxRequestTarget, payload: T) : AbstractPayloadTypedEvent<T>(target, payload)