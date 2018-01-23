package org.kwicket.wicketstuff.select2

import org.apache.wicket.behavior.Behavior
import org.apache.wicket.markup.html.form.FormComponent
import org.apache.wicket.model.IModel
import org.kwicket.component.init
import org.wicketstuff.select2.AbstractSelect2Choice
import org.wicketstuff.select2.ISelect2Theme

fun AbstractSelect2Choice<*, *>.init(
    width: String? = null,
    placeholder: String? = null,
    closeOnSelect: Boolean? = null,
    allowClear: Boolean? = null,
    theme: ISelect2Theme? = null,
    required: Boolean? = null,
    label: IModel<String>? = null,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    escapeModelStrings: Boolean? = null,
    renderBodyOnly: Boolean? = null,
    vararg behaviors: Behavior
) {
    (this as FormComponent<*>).init(
        required = required,
        label = label,
        outputMarkupId = outputMarkupId,
        outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
        visible = visible,
        enabled = enabled,
        escapeModelStrings = escapeModelStrings,
        renderBodyOnly = renderBodyOnly,
        behaviors = *behaviors
    )
    width?.let { settings.width = it }
    placeholder?.let { settings.placeholder = it }
    closeOnSelect?.let { settings.closeOnSelect = it }
    allowClear?.let { settings.allowClear = it }
    theme?.let { settings.theme = it }
}

