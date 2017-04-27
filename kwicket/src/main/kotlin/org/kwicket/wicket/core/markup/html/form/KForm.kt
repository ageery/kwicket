package org.kwicket.wicket.core.markup.html.form

import org.apache.wicket.markup.html.form.Form
import org.apache.wicket.model.IModel
import org.kwicket.component.initComponent

class KForm<T>(id: String, model: IModel<T>? = null, outputMarkupPlaceholderTag: Boolean? = null,
               outputMarkupId: Boolean? = null) : Form<T>(id, model) {
    init {
        initComponent(outputMarkupPlaceholderTag = outputMarkupPlaceholderTag, outputMarkupId = outputMarkupId)
    }
}