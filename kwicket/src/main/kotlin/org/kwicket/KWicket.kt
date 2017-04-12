package org.kwicket

import org.apache.wicket.WicketRuntimeException
import org.apache.wicket.ajax.AjaxRequestTarget

/**
 * Type alias for a non-ajax handler: a lambda with no parameters that returns nothing.
 */
typealias NonAjaxHandler = () -> Unit

/**
 * Type alias for an ajax handler: a lambda with a single parameter of type [AjaxRequestTarget] that returns nothing.
 */
typealias AjaxHandler = (AjaxRequestTarget) -> Unit

/**
 * Defines the pieces of an ajax submit handler: error handler and submit (sucess) handler.
 */
interface IAjaxSubmitHandler {
    /**
     * [AjaxHandler] to invoke when there are errors (e.g., validation violations) in the form.
     */
    val error: AjaxHandler
    /**
     * [AjaxHandler] to invoke when the form is submitted.
     */
    val submit: AjaxHandler
}

/**
 * Defines the pieces of a submit handler: error handler and submit (sucess) handler.
 */
interface INonAjaxSubmitHandler {
    /**
     * [AjaxHandler] to invoke when there are errors (e.g., validation violations) in the form.
     */
    val error: NonAjaxHandler
    /**
     * [AjaxHandler] to invoke when the form is submitted.
     */
    val submit: NonAjaxHandler
}

/**
 * Implementation of [IAjaxSubmitHandler]. An invoked handler that is not specified throws a [WicketRuntimeException].
 */
data class AjaxSubmitHandler(
        override val submit: AjaxHandler = { throw WicketRuntimeException("No submit handler defined") },
        override val error: AjaxHandler = { throw WicketRuntimeException("No error handler defined") }
) : IAjaxSubmitHandler

/**
 * Implementation of [INonAjaxSubmitHandler]. An invoked handler that is not specified throws a [WicketRuntimeException].
 */
data class NonAjaxSubmitHandler(
        override val submit: NonAjaxHandler = { throw WicketRuntimeException("No submit handler defined") },
        override val error: NonAjaxHandler = { throw WicketRuntimeException("No error handler defined") }
) : INonAjaxSubmitHandler
