package org.kwicket.agilecoders.wicket.core.ajax.markup.html.bootstrap.form

import de.agilecoders.wicket.core.markup.html.bootstrap.form.FormGroup
import org.apache.wicket.Component
import org.apache.wicket.behavior.Behavior
import org.apache.wicket.markup.html.WebMarkupContainer
import org.kwicket.component.q
import org.kwicket.wicket.core.markup.html.panel.KPanel

fun <C : Component> WebMarkupContainer.selectFormGroup(
    id: String,
    useFormComponentLabel: Boolean? = null,
    group: (String) -> FormGroup = { KFormGroup(id = it, useFormComponentLabel = useFormComponentLabel) },
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    behaviors: List<Behavior>? = null,
    field: (String) -> C
) =
    q(
        SelectFormGroup(
            id = id,
            useFormComponentLabel = useFormComponentLabel,
            group = group,
            outputMarkupId = outputMarkupId,
            outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
            visible = visible,
            enabled = enabled,
            behaviors = behaviors,
            field = field
        )
    )

class SelectFormGroup<C : Component>(
    id: String,
    useFormComponentLabel: Boolean? = null,
    group: (String) -> FormGroup = { KFormGroup(id = it, useFormComponentLabel = useFormComponentLabel) },
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    behaviors: List<Behavior>? = null,
    field: (String) -> C
) : KPanel(
    id = id,
    outputMarkupId = outputMarkupId,
    outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
    visible = visible,
    enabled = enabled,
    behaviors = behaviors
) {

    val field: C

    init {
        q(group("group"))
        this.field = q(field("field"))
    }

}