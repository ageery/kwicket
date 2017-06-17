package org.kwicket.wicket.core.markup.html.basic

import org.apache.wicket.behavior.Behavior
import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.model.IModel
import org.kwicket.component.initComponent

class KLabel(id: String,
             model: IModel<*>? = null,
             outputMarkupId: Boolean? = null,
             outputMarkupPlaceholderTag: Boolean? = null,
             visible: Boolean? = null,
             enabled: Boolean? = null,
             vararg behaviors: Behavior)
    : Label(id, model) {

    init {
        initComponent(outputMarkupId = outputMarkupId,
                outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
                visible = visible,
                enabled = enabled,
                behaviors = *behaviors)
    }

}