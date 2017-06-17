package org.kwicket.wicket.core.markup.html.form

import org.apache.wicket.behavior.Behavior
import org.apache.wicket.markup.html.form.TextField
import org.apache.wicket.model.IModel
import org.kwicket.component.initComponent
import org.kwicket.component.initFormComponent

open class KTextField<T>(id: String,
                         model: IModel<T?>? = null,
                         type: Class<T>? = null,
                         required: Boolean? = null,
                         outputMarkupId: Boolean? = null,
                         outputMarkupPlaceholderTag: Boolean? = null,
                         label: IModel<String>? = null,
                         visible: Boolean? = null,
                         enabled: Boolean? = null,
                         vararg behaviors: Behavior)
    : TextField<T>(id, model, type) {

    init {
        initComponent(outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
                outputMarkupId = outputMarkupId,
                visible = visible,
                enabled = enabled,
                behaviors = *behaviors)
        initFormComponent(required = required, label = label)
    }

}