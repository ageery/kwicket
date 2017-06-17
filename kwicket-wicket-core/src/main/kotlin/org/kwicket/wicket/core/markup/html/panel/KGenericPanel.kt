package org.kwicket.wicket.core.markup.html.panel

import org.apache.wicket.behavior.Behavior
import org.apache.wicket.markup.html.panel.GenericPanel
import org.apache.wicket.model.IModel
import org.kwicket.component.initComponent

open class KGenericPanel<T>(id: String,
                            model: IModel<T>? = null,
                            outputMarkupId: Boolean? = null,
                            outputMarkupPlaceholderTag: Boolean? = null,
                            vararg behaviors: Behavior) :
        GenericPanel<T>(id, model) {

    init {
        initComponent(outputMarkupId = outputMarkupId,
                outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
                behaviors = *behaviors)
    }

}