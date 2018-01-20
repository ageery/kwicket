package org.kwicket.wicket.core.markup.html.form

import org.apache.wicket.behavior.Behavior
import org.apache.wicket.markup.html.form.TextArea
import org.apache.wicket.model.IModel
import org.kwicket.component.init

/**
 * [TextArea] with named and default constructor arguments.
 */
open class KTextArea<T>(id: String,
                     model: IModel<T>,
                     required: Boolean? = null,
                     outputMarkupId: Boolean? = null,
                     outputMarkupPlaceholderTag: Boolean? = null,
                     label: IModel<String>? = null,
                     vararg behaviors: Behavior
) : TextArea<T>(id, model) {

    init {
        init(outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
                outputMarkupId = outputMarkupId,
                behaviors = *behaviors,
                required = required,
                label = label)
    }

}