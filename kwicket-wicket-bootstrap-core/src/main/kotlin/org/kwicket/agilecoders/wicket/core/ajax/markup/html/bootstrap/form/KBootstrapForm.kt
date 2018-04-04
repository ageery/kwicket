package org.kwicket.agilecoders.wicket.core.ajax.markup.html.bootstrap.form

import de.agilecoders.wicket.core.markup.html.bootstrap.form.BootstrapForm
import de.agilecoders.wicket.core.markup.html.bootstrap.form.FormType
import org.apache.wicket.behavior.Behavior
import org.apache.wicket.model.IModel
import org.kwicket.component.init

open class KBootstrapForm<T>(
    id: String,
    model: IModel<T>? = null,
    type: FormType? = null,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    behaviors: List<Behavior>? = null
) : BootstrapForm<T>(id, model) {

    init {
        init(
            outputMarkupId = outputMarkupId,
            outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
            behaviors = behaviors
        )
        type?.let { type(it) }
    }

}