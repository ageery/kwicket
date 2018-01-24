package org.kwicket.sample

import kotlinx.html.div
import org.apache.wicket.model.IModel
import org.kwicket.builder.MarkupProviderPanel
import org.kwicket.builder.PANEL
import org.kwicket.builder.span
import org.kwicket.component.q
import org.kwicket.model.model
import org.kwicket.wicket.core.markup.html.basic.KLabel

class NamePanel(id: String, model: IModel<String>) : MarkupProviderPanel(id = id, model = model) {

    companion object {
        const val labelId = "label"
        const val nameId = "name"
    }

    override val markup: PANEL.() -> Unit = {
        div {
            span(id = labelId)
            +": "
            span(id = nameId)
        }
    }

    init {
        q(KLabel(id = labelId, model = "My Name".model()))
        q(KLabel(id = nameId, model = model))
    }

}