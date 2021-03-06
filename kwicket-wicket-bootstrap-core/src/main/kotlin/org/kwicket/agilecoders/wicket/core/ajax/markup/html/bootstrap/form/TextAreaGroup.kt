package org.kwicket.agilecoders.wicket.core.ajax.markup.html.bootstrap.form

import de.agilecoders.wicket.core.markup.html.bootstrap.form.FormGroup
import org.apache.wicket.Component
import org.apache.wicket.MarkupContainer
import org.apache.wicket.behavior.Behavior
import org.kwicket.component.q
import org.kwicket.wicket.core.markup.html.panel.KPanel

@Deprecated(message = "Use the method in the queued package", replaceWith = ReplaceWith(expression = "org.kwicket.agilecoders.queued.textAreaGroup"))
fun <C : Component> MarkupContainer.textAreaGroup(
    id: String,
    useFormComponentLabel: Boolean? = null,
    group: (String) -> FormGroup = { KFormGroup(id = it, useFormComponentLabel = useFormComponentLabel) },
    field: (String) -> C,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    behavior: Behavior
) = q(
    TextAreaGroup(
        id = id,
        useFormComponentLabel = useFormComponentLabel,
        group = group,
        field = field,
        outputMarkupId = outputMarkupId,
        outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
        visible = visible,
        enabled = enabled,
        behavior = behavior
    )
)

class TextAreaGroup<C : Component>(
    id: String,
    useFormComponentLabel: Boolean? = null,
    group: (String) -> FormGroup = { KFormGroup(id = it, useFormComponentLabel = useFormComponentLabel) },
    field: (String) -> C,
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

    constructor(
        id: String,
        useFormComponentLabel: Boolean? = null,
        group: (String) -> FormGroup = { KFormGroup(id = it, useFormComponentLabel = useFormComponentLabel) },
        field: (String) -> C,
        outputMarkupId: Boolean? = null,
        outputMarkupPlaceholderTag: Boolean? = null,
        visible: Boolean? = null,
        enabled: Boolean? = null,
        behavior: Behavior
    ) : this(
        id = id,
        useFormComponentLabel = useFormComponentLabel,
        group = group,
        field = field,
        outputMarkupId = outputMarkupId,
        outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
        visible = visible,
        enabled = enabled,
        behaviors = listOf(behavior)
    )

    val field: C

    init {
        q(group("group"))
        this.field = q(field("field"))
    }

}