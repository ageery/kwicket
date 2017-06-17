package org.kwicket.wicket.core.markup.html.form

import org.apache.wicket.behavior.Behavior
import org.apache.wicket.markup.html.form.CheckBox
import org.apache.wicket.model.IModel
import org.kwicket.component.initComponent
import org.kwicket.component.initFormComponent

class KCheckbox(id: String,
                model: IModel<Boolean>,
                outputMarkupPlaceholderTag: Boolean? = null,
                outputMarkupId: Boolean? = null,
                label: IModel<String>? = null,
                vararg behaviors: Behavior) : CheckBox(id, model) {

    init {
        initComponent(outputMarkupPlaceholderTag = outputMarkupPlaceholderTag, outputMarkupId = outputMarkupId, behaviors = *behaviors)
        initFormComponent(label = label)
    }
}