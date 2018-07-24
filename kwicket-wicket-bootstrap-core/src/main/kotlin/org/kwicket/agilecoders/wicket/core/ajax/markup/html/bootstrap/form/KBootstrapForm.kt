package org.kwicket.agilecoders.wicket.core.ajax.markup.html.bootstrap.form

import de.agilecoders.wicket.core.markup.html.bootstrap.form.BootstrapForm
import de.agilecoders.wicket.core.markup.html.bootstrap.form.FormType
import org.apache.wicket.behavior.Behavior
import org.apache.wicket.markup.html.WebMarkupContainer
import org.apache.wicket.markup.html.form.validation.IFormValidator
import org.apache.wicket.model.IModel
import org.kwicket.component.init
import org.kwicket.component.q

@Deprecated(replaceWith = ReplaceWith("org.kwicket.agilecoders.queued.bootstrapForm"), message = "Use in different package")
fun <T> WebMarkupContainer.bootstrapForm(
    id: String,
    model: IModel<T>? = null,
    type: FormType? = null,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    behaviors: List<Behavior>? = null,
    validators: List<IFormValidator>? = null
) = q(
    KBootstrapForm(
        id = id,
        model = model,
        type = type,
        outputMarkupId = outputMarkupId,
        outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
        behaviors = behaviors,
        validators = validators
    )
)

open class KBootstrapForm<T>(
    id: String,
    model: IModel<T>? = null,
    type: FormType? = null,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    behaviors: List<Behavior>? = null,
    validators: List<IFormValidator>? = null
) : BootstrapForm<T>(id, model) {

    init {
        init(
            outputMarkupId = outputMarkupId,
            outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
            behaviors = behaviors
        )
        type?.let { type(it) }
        validators?.forEach { add(it) }
    }

}