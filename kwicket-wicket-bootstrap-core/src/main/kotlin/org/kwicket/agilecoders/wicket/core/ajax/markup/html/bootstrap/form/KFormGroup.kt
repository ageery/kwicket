package org.kwicket.agilecoders.wicket.core.ajax.markup.html.bootstrap.form

import de.agilecoders.wicket.core.markup.html.bootstrap.form.FormGroup
import org.apache.wicket.behavior.Behavior
import org.apache.wicket.model.IModel
import org.kwicket.component.init
import org.kwicket.model.model

open class KFormGroup(
    id: String,
    label: IModel<String> = "".model(),
    help: IModel<String> = "".model(),
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    useFormComponentLabel: Boolean? = null,
    behaviors: List<Behavior>? = null
) : FormGroup(id, label, help) {

    constructor(
        id: String,
        label: IModel<String> = "".model(),
        help: IModel<String> = "".model(),
        outputMarkupId: Boolean? = null,
        outputMarkupPlaceholderTag: Boolean? = null,
        visible: Boolean? = null,
        enabled: Boolean? = null,
        useFormComponentLabel: Boolean? = null,
        behavior: Behavior
    ) : this(
        id = id,
        label = label,
        help = help,
        useFormComponentLabel = useFormComponentLabel,
        outputMarkupId = outputMarkupId,
        outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
        visible = visible,
        enabled = enabled,
        behaviors = listOf(behavior)
    )

    init {
        init(
            outputMarkupId = outputMarkupId,
            outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
            visible = visible,
            enabled = enabled,
            behaviors = behaviors
        )
        useFormComponentLabel?.let { this.useFormComponentLabel(it) }
    }

}