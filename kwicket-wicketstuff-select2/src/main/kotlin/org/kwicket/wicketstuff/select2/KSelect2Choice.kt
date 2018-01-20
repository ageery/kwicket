package org.kwicket.wicketstuff.select2

import org.apache.wicket.behavior.Behavior
import org.apache.wicket.model.IModel
import org.wicketstuff.select2.ChoiceProvider
import org.wicketstuff.select2.ISelect2Theme
import org.wicketstuff.select2.Select2Choice

open class KSelect2Choice<T>(id: String,
                             model: IModel<T?>,
                             choiceProvider: ChoiceProvider<T>,
                             label: IModel<String>? = null,
                             required: Boolean? = null,
                             width: String? = null,
                             closeOnSelect: Boolean? = null,
                             allowClear: Boolean? = null,
                             placeholder: String? = "",
                             theme: ISelect2Theme? = null,
                             outputMarkupId: Boolean? = null,
                             outputMarkupPlaceholderId: Boolean? = null,
                             vararg behaviors: Behavior)
    : Select2Choice<T>(id, model, choiceProvider) {

    init {
        init(width = width,
                closeOnSelect = closeOnSelect,
                allowClear = allowClear,
                placeholder = placeholder,
                theme = theme,
                outputMarkupId = outputMarkupId,
                outputMarkupPlaceholderTag = outputMarkupPlaceholderId,
                behaviors = *behaviors,
                label = label,
                required = required)
    }

}