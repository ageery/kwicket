package org.kwicket

import org.apache.wicket.ajax.AjaxRequestTarget

/**
 * Type alias for an ajax handler: a lambda with a single parameter of type [AjaxRequestTarget] that returns nothing.
 */
typealias AjaxHandler = (AjaxRequestTarget) -> Unit

typealias AjaxClickHandler<T> = (AjaxRequestTarget, T?) -> Unit

/**
 * Type alias for a non-ajax handler: a lambda with no parameters that returns nothing.
 */
typealias NonAjaxHandler = () -> Unit

typealias NonAjaxClickHandler<T> = (T?) -> Unit
