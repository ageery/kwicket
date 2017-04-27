package org.kwicket.wicket.core.markup.html.panel

import org.apache.wicket.behavior.Behavior
import org.apache.wicket.markup.html.panel.Panel
import org.apache.wicket.model.IModel
import org.kwicket.component.initComponent

open class KPanel(id: String,
                  model: IModel<*>?,
                  outputMarkupId: Boolean? = null,
                  outputMarkupPlaceholderTag: Boolean? = null,
                  vararg behaviors: Behavior)
    : Panel(id, model) {
    init {
        initComponent(outputMarkupId = outputMarkupId,
                outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
                behaviors = *behaviors)
    }
}