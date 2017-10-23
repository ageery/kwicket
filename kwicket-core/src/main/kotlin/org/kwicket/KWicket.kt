package org.kwicket

import com.github.openjson.JSONObject
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

/**
 * Converts an object to a JSON representation.
 * @receiver Any object
 * @param indent optional amount to indent the JSON by
 * @return JSON representation of the @receiver
 */
fun Any.toJson(indent: Int? = null): String {
    val json = if (this is Map<*, *>) JSONObject(this) else JSONObject(this)
    return indent?.let { json.toString(it) } ?: json.toString()
}
