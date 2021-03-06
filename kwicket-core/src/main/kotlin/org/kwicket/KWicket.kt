package org.kwicket

import com.github.openjson.JSONObject
import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.markup.head.CssHeaderItem
import org.apache.wicket.request.cycle.RequestCycle
import org.apache.wicket.request.handler.resource.ResourceReferenceRequestHandler
import org.apache.wicket.request.mapper.parameter.PageParameters
import org.apache.wicket.request.resource.CssResourceReference
import org.apache.wicket.request.resource.PackageResourceReference
import org.apache.wicket.request.resource.ResourceReference
import org.apache.wicket.util.resource.StringResourceStream
import org.apache.wicket.util.time.Duration
import kotlin.reflect.KClass

/**
 * Name of Wicket's id attribute.
 */
const val wicketNamespacePrefix = "wicket"
const val wicketIdAttr = "${wicketNamespacePrefix}:id"

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
// TODO: in addition, should we just make it transparent that we use the standard localdatetime stuff?

fun Int.secs() = Duration.seconds(this)
fun Double.secs() = Duration.seconds(this)

fun Int.millis() = Duration.milliseconds(this.toLong())

operator fun Duration.plus(dur: Duration) = this.add(dur)
operator fun Duration.minus(dur: Duration) = this.subtract(dur)

fun List<Pair<String, *>>.toParams(): PageParameters {
    val pageParams = PageParameters()
    this.forEach { pageParams.add(it.first, it.second) }
    return pageParams
}

fun Pair<String, *>.toParams() = listOf(this).toParams()

// FIXME: this is duplicated
fun KClass<*>.resRef(path: String) = PackageResourceReference(java, path)
fun KClass<*>.toCssHeader(path: String) = CssHeaderItem.forReference(CssResourceReference(java, path))
fun ResourceReference.toUrl() = RequestCycle.get()
    .urlFor(ResourceReferenceRequestHandler(this)).toString()