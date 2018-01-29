package org.kwicket.sample

import kotlinx.html.div
import org.apache.wicket.model.IModel
import org.kwicket.kotlinx.html.MarkupProviderPanel
import org.kwicket.kotlinx.html.PanelTag
import org.kwicket.kotlinx.html.span
import org.kwicket.component.q
import org.kwicket.model.model
import org.kwicket.wicket.core.markup.html.basic.KLabel

class NamePanel(id: String, model: IModel<String>) : MarkupProviderPanel(id = id, model = model) {

    companion object {
        const val labelId = "label"
        const val nameId = "name"
    }

    override val markup: PanelTag.() -> Unit = {
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