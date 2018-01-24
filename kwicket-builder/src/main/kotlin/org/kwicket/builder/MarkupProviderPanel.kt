package org.kwicket.builder

import org.apache.wicket.MarkupContainer
import org.apache.wicket.markup.IMarkupResourceStreamProvider
import org.apache.wicket.model.IModel
import org.apache.wicket.util.resource.IResourceStream
import org.kwicket.wicket.core.markup.html.panel.KPanel

abstract class MarkupProviderPanel(id: String, model: IModel<*>) : KPanel(id = id, model = model), IMarkupResourceStreamProvider {

    abstract val markup: PANEL.() -> Unit

    override fun getMarkupResourceStream(container: MarkupContainer?, containerClass: Class<*>?): IResourceStream =
            panel(block = markup)

}