package org.kwicket.kotlinx.html

import org.apache.wicket.MarkupContainer
import org.apache.wicket.markup.IMarkupResourceStreamProvider
import org.apache.wicket.model.IModel
import org.apache.wicket.util.resource.IResourceStream
import org.kwicket.toResourceStream
import org.kwicket.wicket.core.markup.html.panel.KPanel

data class RegionInfo(val markup: String, val rootComponentBuilders: List<ComponentBuilder>)

open class RegionInfoPanel<T : Any>(id: String, model: IModel<T>, region: (IModel<T>) -> RegionInfo) :
    KPanel(id = id, model = model), IMarkupResourceStreamProvider {

    private val markup: String

    init {
        val regionInfo = region(model)
        markup = regionInfo.markup
        regionInfo.rootComponentBuilders.forEach { it.addTo(this) }
    }

    override fun getMarkupResourceStream(container: MarkupContainer, containerClass: Class<*>): IResourceStream =
        markup.toResourceStream()

}