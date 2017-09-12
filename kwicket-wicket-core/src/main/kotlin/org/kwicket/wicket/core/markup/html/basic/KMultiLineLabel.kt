package org.kwicket.wicket.core.markup.html.basic

import org.apache.wicket.behavior.Behavior
import org.apache.wicket.markup.html.basic.MultiLineLabel
import org.apache.wicket.model.IModel
import org.kwicket.component.init

/**
 * [MultiLineLabel] with named and default constructor arguments.
 */
open class KMultiLineLabel(id: String,
                           model: IModel<String>,
                           outputMarkupId: Boolean? = null,
                           outputMarkupPlaceholderTag: Boolean? = null,
                           visible: Boolean? = null,
                           enabled: Boolean? = null,
                           escapeModelStrings: Boolean? = null,
                           renderBodyOnly: Boolean? = null,
                           vararg behaviors: Behavior)
    : MultiLineLabel(id, model) {

    init {
        init(outputMarkupId = outputMarkupId,
                outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
                visible = visible,
                enabled = enabled,
                escapeModelStrings = escapeModelStrings,
                renderBodyOnly = renderBodyOnly,
                behaviors = *behaviors)
    }

}
