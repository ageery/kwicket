package org.kwicket.wicketstuff.select2

import org.apache.wicket.behavior.Behavior
import org.apache.wicket.model.IModel
import org.wicketstuff.select2.ChoiceProvider
import org.wicketstuff.select2.ISelect2Theme
import org.wicketstuff.select2.Select2MultiChoice

open class KSelect2MultiChoice<T>(
    id: String,
    model: IModel<MutableCollection<T>>,
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
    behaviors: List<Behavior>? = null
) : Select2MultiChoice<T>(id, model, choiceProvider) {

    init {
        init(
            width = width,
            closeOnSelect = closeOnSelect,
            allowClear = allowClear,
            placeholder = placeholder,
            theme = theme,
            outputMarkupId = outputMarkupId,
            outputMarkupPlaceholderTag = outputMarkupPlaceholderId,
            behaviors = behaviors,
            label = label,
            required = required
        )
    }

}