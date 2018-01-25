package org.kwicket

import com.github.openjson.JSONObject
import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.util.resource.StringResourceStream
import org.apache.wicket.util.time.Duration

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

/**
 * Converts a [String] to an [IResourceStream].
 * @receiver [String] to convert to an [IResourceStream]
 * @param contentType optional mime type of the resource, such as "image/jpeg" or "text/html"
 * @return [IResourceStream] for the @receiver
 */
fun String.toResourceStream(contentType: String? = null) = StringResourceStream(this, contentType)

// TODO: add extension methods for minutes, hours, days + years

fun Int.secs() = Duration.seconds(this)
fun Double.secs() = Duration.seconds(this)

fun Int.millis() = Duration.milliseconds(this.toLong())

operator fun Duration.plus(dur: Duration) = this.add(dur)
operator fun Duration.minus(dur: Duration) = this.subtract(dur)
