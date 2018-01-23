package org.kwicket.wicket.core.markup.html.form

import org.apache.wicket.markup.html.form.Form
import org.apache.wicket.markup.html.form.validation.IFormValidator
import org.apache.wicket.model.IModel
import org.kwicket.component.init

/**
 * [Form] with named and default constructor arguments.
 */
open class KForm<T>(
    id: String,
    model: IModel<T>? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    outputMarkupId: Boolean? = null,
    vararg validators: IFormValidator
) : Form<T>(id, model) {

    init {
        init(outputMarkupPlaceholderTag = outputMarkupPlaceholderTag, outputMarkupId = outputMarkupId)
        validators.forEach { add(it) }
    }
}