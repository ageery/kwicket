package org.kwicket.wicket.extensions.markup.html.form

import org.apache.wicket.behavior.Behavior
import org.apache.wicket.extensions.markup.html.form.DateTextField
import org.apache.wicket.model.IModel
import org.kwicket.component.init
import org.kwicket.model.toDateModel
import java.time.LocalDate

class KDateTextField(
    id: String,
    model: IModel<LocalDate?>,
    pattern: String,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    escapeModelStrings: Boolean? = null,
    renderBodyOnly: Boolean? = null,
    behaviors: List<Behavior>? = null
) : DateTextField(id, toDateModel(model), pattern) {

    init {
        init(
            outputMarkupId = outputMarkupId,
            outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
            visible = visible,
            enabled = enabled,
            escapeModelStrings = escapeModelStrings,
            renderBodyOnly = renderBodyOnly,
            behaviors = behaviors
        )
    }

}