package org.kwicket.wicket.core.markup.html.basic

import org.apache.wicket.behavior.Behavior
import org.apache.wicket.markup.html.basic.MultiLineLabel
import org.apache.wicket.model.IModel
import org.kwicket.component.initComponent

open class KMultiLineLabel(id: String,
                           model: IModel<String>,
                           outputMarkupId: Boolean? = null,
                           outputMarkupPlaceholderTag: Boolean? = null,
                           visible: Boolean? = null,
                           enabled: Boolean? = null,
                           escapeModelStrings: Boolean? = null,
                           vararg behaviors: Behavior)
    : MultiLineLabel(id, model) {

    init {
        initComponent(outputMarkupId = outputMarkupId,
                outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
                visible = visible,
                enabled = enabled,
                escapeModelStrings = escapeModelStrings,
                behaviors = *behaviors)
    }

}
