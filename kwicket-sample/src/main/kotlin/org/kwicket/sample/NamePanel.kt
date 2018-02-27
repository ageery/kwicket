package org.kwicket.sample

import kotlinx.html.div
import org.apache.wicket.model.IModel
import org.kwicket.component.q
import org.kwicket.kotlinx.html.PanelTagPanel
import org.kwicket.kotlinx.html.PanelTag
import org.kwicket.kotlinx.html.panel
import org.kwicket.kotlinx.html.span
import org.kwicket.model.model
import org.kwicket.wicket.core.markup.html.basic.KLabel

class NamePanel(id: String, model: IModel<String>) : PanelTagPanel(id = id, model = model) {

    companion object {
        const val labelId = "label"
        const val nameId = "name"
    }

    override val markup: PanelTag.() -> Unit = {
        panel {
            div {
                span(id = labelId)
                +": "
                span(id = nameId)
            }
        }
    }

    init {
        q(KLabel(id = labelId, model = "My Name".model()))
        q(KLabel(id = nameId, model = model))
    }

}