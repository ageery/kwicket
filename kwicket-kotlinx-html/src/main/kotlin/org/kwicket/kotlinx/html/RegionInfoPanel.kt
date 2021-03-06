package org.kwicket.kotlinx.html

import org.apache.wicket.MarkupContainer
import org.apache.wicket.behavior.Behavior
import org.apache.wicket.markup.IMarkupCacheKeyProvider
import org.apache.wicket.markup.IMarkupResourceStreamProvider
import org.apache.wicket.model.IModel
import org.apache.wicket.util.resource.IResourceStream
import org.kwicket.toResourceStream
import org.kwicket.wicket.core.markup.html.panel.KPanel

data class RegionInfo(val markup: String, val rootComponentBuilders: List<ComponentBuilder>)

//@Deprecated(message = "Use the method in the queued package", replaceWith = ReplaceWith(expression = "org.kwicket.kotlinx.html.queued.inlinePanel"))
fun <T> inlinePanel(
    id: String,
    model: IModel<T>,
    visible: Boolean? = null,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    renderBodyOnly: Boolean? = null,
    behaviors: List<Behavior>? = null,
    panel: PANEL.(IModel<T>) -> Unit
) = RegionInfoPanel(
    id = id,
    model = model,
    visible = visible,
    outputMarkupId = outputMarkupId,
    outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
    renderBodyOnly = renderBodyOnly,
    behaviors = behaviors,
    region = { region().panel { panel(model) } }
)

//@Deprecated(message = "Use the method in the queued package", replaceWith = ReplaceWith(expression = "org.kwicket.kotlinx.html.queued.inlinePanel"))
fun <T> inlinePanel(
    id: String,
    model: IModel<T>,
    visible: Boolean? = null,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    renderBodyOnly: Boolean? = null,
    behavior: Behavior,
    panel: PANEL.(IModel<T>) -> Unit
) = inlinePanel(
    id = id,
    model = model,
    visible = visible,
    outputMarkupId = outputMarkupId,
    outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
    renderBodyOnly = renderBodyOnly,
    behaviors = listOf(behavior),
    panel = panel
)

open class RegionInfoPanel<T>(
    id: String,
    model: IModel<T>,
    visible: Boolean? = null,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    renderBodyOnly: Boolean? = null,
    behaviors: List<Behavior>? = null,
    region: (IModel<T>) -> RegionInfo
) :
    KPanel(
        id = id,
        model = model,
        visible = visible,
        outputMarkupId = outputMarkupId,
        outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
        renderBodyOnly = renderBodyOnly,
        behaviors = behaviors
    ), IMarkupResourceStreamProvider, IMarkupCacheKeyProvider {

    /*private*/ val markup: String

    init {
        val regionInfo = region(model)
        markup = regionInfo.markup
        regionInfo.rootComponentBuilders.forEach { it.addTo(this) }
    }

    override fun getMarkupResourceStream(container: MarkupContainer, containerClass: Class<*>): IResourceStream =
        markup.toResourceStream()

    // FIXME: you really want to cache this on a per instance vs per class basis
    override fun getCacheKey(container: MarkupContainer, containerClass: Class<*>): String? = null
}