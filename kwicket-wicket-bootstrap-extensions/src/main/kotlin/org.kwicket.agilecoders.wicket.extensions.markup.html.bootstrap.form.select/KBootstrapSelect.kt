package org.kwicket.agilecoders.wicket.extensions.markup.html.bootstrap.form.select

import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.select.BootstrapSelect
import org.apache.wicket.behavior.Behavior
import org.apache.wicket.markup.html.form.IChoiceRenderer
import org.apache.wicket.model.IModel
import org.kwicket.component.init

open class KBootstrapSelect<T>(
    id: String,
    choices: List<T>,
    renderer: IChoiceRenderer<T>,
    model: IModel<T>? = null,
    config: KBootstrapSelectConfig? = null,
    required: Boolean? = null,
    nullValid: Boolean? = null,
    label: IModel<String>? = null,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    escapeModelStrings: Boolean? = null,
    renderBodyOnly: Boolean? = null,
    behaviors: List<Behavior>? = null
) : BootstrapSelect<T>(id, model, choices, renderer) {

    init {
        init(
            outputMarkupId = outputMarkupId,
            outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
            visible = visible,
            enabled = enabled,
            escapeModelStrings = escapeModelStrings,
            renderBodyOnly = renderBodyOnly,
            behaviors = behaviors,
            required = required,
            label = label
        )
        config?.let { with(it) }
        nullValid?.let { setNullValid(it) }
    }

}