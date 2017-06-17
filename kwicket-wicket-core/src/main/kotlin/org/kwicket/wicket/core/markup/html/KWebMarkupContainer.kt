package org.kwicket.wicket.core.markup.html

import org.apache.wicket.behavior.Behavior
import org.apache.wicket.markup.html.WebMarkupContainer
import org.apache.wicket.model.IModel
import org.kwicket.component.initComponent

class KWebMarkupContainer(id: String,
                          model: IModel<*>? = null,
                          outputMarkupId: Boolean? = null,
                          outputMarkupPlaceholderTag: Boolean? = null,
                          vararg behaviors: Behavior)
    : WebMarkupContainer(id, model) {

    init {
        initComponent(outputMarkupId = outputMarkupId,
                outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
                behaviors = *behaviors)
    }

}