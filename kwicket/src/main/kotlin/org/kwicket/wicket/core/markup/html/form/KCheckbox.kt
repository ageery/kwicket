package org.kwicket.wicket.core.markup.html.form

import org.apache.wicket.markup.html.form.CheckBox
import org.apache.wicket.model.IModel
import org.kwicket.component.initComponent

class KCheckbox(id: String, model: IModel<Boolean>, outputMarkupPlaceholderTag: Boolean? = null,
                outputMarkupId: Boolean? = null) : CheckBox(id, model) {

    init {
        initComponent(outputMarkupPlaceholderTag = outputMarkupPlaceholderTag, outputMarkupId = outputMarkupId)
    }
}