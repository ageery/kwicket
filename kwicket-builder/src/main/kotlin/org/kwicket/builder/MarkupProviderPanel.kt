package org.kwicket.builder

import org.apache.wicket.MarkupContainer
import org.apache.wicket.markup.IMarkupResourceStreamProvider
import org.apache.wicket.model.IModel
import org.apache.wicket.util.resource.IResourceStream
import org.kwicket.wicket.core.markup.html.panel.KPanel

enum class ProviderType {
    Panel,
    Extension
}

abstract class MarkupProviderPanel(id: String, model: IModel<*>, val type: ProviderType = ProviderType.Panel) : KPanel(id = id, model = model), IMarkupResourceStreamProvider {

    abstract val markup: PanelTag.() -> Unit

    override fun getMarkupResourceStream(container: MarkupContainer?, containerClass: Class<*>?): IResourceStream =
        if (type == ProviderType.Panel) panel(block = markup) else extend(block = markup)

}