package org.kwicket.sample

import kotlinx.html.div
import kotlinx.html.html
import kotlinx.html.stream.appendHTML
import org.apache.wicket.MarkupContainer
import org.apache.wicket.markup.IMarkupResourceStreamProvider
import org.apache.wicket.model.IModel
import org.apache.wicket.util.resource.IResourceStream
import org.kwicket.builder.panel
import org.kwicket.builder.span
import org.kwicket.component.q
import org.kwicket.model.model
import org.kwicket.toResourceStream
import org.kwicket.wicket.core.markup.html.basic.KLabel
import org.kwicket.wicket.core.markup.html.panel.KPanel

class NamePanel(id: String, model: IModel<String>) :
    KPanel(id = id, model = model), IMarkupResourceStreamProvider {

    companion object {
        const val labelId = "label"
        const val nameId = "name"
    }

    init {
        q(KLabel(id = labelId, model = "My Name".model()))
        q(KLabel(id = nameId, model = model))
    }

    override fun getMarkupResourceStream(container: MarkupContainer, containerClass: Class<*>): IResourceStream =
        buildString {
            appendHTML().html {
                panel {
                    div {
                        span(id = labelId)
                        +": "
                        span(id = nameId)
                    }
                }
            }
        }.toResourceStream()
}