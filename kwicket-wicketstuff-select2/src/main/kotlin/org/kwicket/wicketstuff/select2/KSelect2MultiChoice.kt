package org.kwicket.wicketstuff.select2

import org.apache.wicket.behavior.Behavior
import org.apache.wicket.model.IModel
import org.kwicket.component.initComponent
import org.kwicket.component.initFormComponent
import org.wicketstuff.select2.ChoiceProvider
import org.wicketstuff.select2.ISelect2Theme
import org.wicketstuff.select2.Select2MultiChoice

open class KSelect2MultiChoice<T>(id: String,
                             model: IModel<Collection<T>>,
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
    : Select2MultiChoice<T>(id, model, choiceProvider) {

    init {
        initSelect2(width = width, closeOnSelect = closeOnSelect, allowClear = allowClear,
                placeholder = placeholder, theme = theme)
        initComponent(outputMarkupId = outputMarkupId, outputMarkupPlaceholderTag = outputMarkupPlaceholderId,
                behaviors = *behaviors)
        initFormComponent(label = label, required = required)
    }

}