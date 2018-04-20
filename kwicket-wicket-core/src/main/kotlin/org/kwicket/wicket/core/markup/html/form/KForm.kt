package org.kwicket.wicket.core.markup.html.form

import org.apache.wicket.behavior.Behavior
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
    behaviors: List<Behavior>? = null,
    validators: List<IFormValidator>? = null
) : Form<T>(id, model) {

    init {
        init(
            outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
            outputMarkupId = outputMarkupId,
            behaviors = behaviors
        )
        validators?.forEach { add(it) }
    }
}