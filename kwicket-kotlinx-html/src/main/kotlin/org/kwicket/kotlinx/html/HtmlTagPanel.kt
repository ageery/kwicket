package org.kwicket.kotlinx.html

import kotlinx.html.HTMLTag
import kotlinx.html.stream.appendHTML
import org.apache.wicket.MarkupContainer
import org.apache.wicket.markup.IMarkupResourceStreamProvider
import org.apache.wicket.model.IModel
import org.apache.wicket.util.resource.IResourceStream
import org.kwicket.toResourceStream
import org.kwicket.wicket.core.markup.html.panel.KPanel

abstract class HtmlTagPanel<T : HTMLTag>(id: String, model: IModel<*>) : KPanel(id = id, model = model),
    IMarkupResourceStreamProvider {

    abstract val markup: T.() -> Unit
    abstract val container: (T.() -> Unit) -> String

    override fun getMarkupResourceStream(container: MarkupContainer, containerClass: Class<*>): IResourceStream =
        container(markup).toResourceStream()

}

abstract class PanelTagPanel(id: String, model: IModel<*>) : HtmlTagPanel<PanelTag>(id = id, model = model) {

    override val container: (PanelTag.() -> Unit) -> String = {
        buildString {
            appendHTML().panel(block = it)
        }
    }

}

abstract class ExtendTagPanel(id: String, model: IModel<*>) : HtmlTagPanel<PanelTag>(id = id, model = model) {

    override val container: (PanelTag.() -> Unit) -> String = {
        buildString {
            appendHTML().extend(block = it)
        }
    }

}