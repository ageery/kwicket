package org.kwicket.agilecoders.wicket.core.ajax.markup.html.bootstrap.form

import de.agilecoders.wicket.core.markup.html.bootstrap.form.FormGroup
import org.apache.wicket.Component
import org.apache.wicket.behavior.Behavior
import org.apache.wicket.model.IModel
import org.kwicket.component.q
import org.kwicket.wicket.core.markup.html.panel.KPanel

class InputFormGroup(
    id: String,
    useFormComponentLabel: Boolean? = null,
    group: (String) -> FormGroup = { KFormGroup(id = it, useFormComponentLabel = useFormComponentLabel) },
    field: (String) -> Component,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    behaviors: List<Behavior>? = null
) : KPanel(
    id = id,
    outputMarkupId = outputMarkupId,
    outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
    visible = visible,
    enabled = enabled,
    behaviors = behaviors
) {

    val field: Component

    init {
        q(group("group"))
        this.field = q(field("field"))
    }

}