package org.kwicket.agilecoders.queued

import de.agilecoders.wicket.core.markup.html.bootstrap.form.FormGroup
import de.agilecoders.wicket.core.markup.html.bootstrap.form.FormType
import org.apache.wicket.Component
import org.apache.wicket.MarkupContainer
import org.apache.wicket.behavior.Behavior
import org.apache.wicket.markup.html.form.validation.IFormValidator
import org.apache.wicket.model.IModel
import org.kwicket.agilecoders.wicket.core.ajax.markup.html.bootstrap.form.FileFormGroup
import org.kwicket.agilecoders.wicket.core.ajax.markup.html.bootstrap.form.InputFormGroup
import org.kwicket.agilecoders.wicket.core.ajax.markup.html.bootstrap.form.KBootstrapForm
import org.kwicket.agilecoders.wicket.core.ajax.markup.html.bootstrap.form.KFormGroup
import org.kwicket.agilecoders.wicket.core.ajax.markup.html.bootstrap.form.SelectFormGroup
import org.kwicket.agilecoders.wicket.core.ajax.markup.html.bootstrap.form.TextAreaGroup
import org.kwicket.component.q
import org.kwicket.model.model

fun <T> MarkupContainer.bootstrapForm(
    id: String,
    model: IModel<T>? = null,
    type: FormType? = null,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    behaviors: List<Behavior>? = null,
    validators: List<IFormValidator>? = null
) = q(
    KBootstrapForm(
        id = id,
        model = model,
        type = type,
        outputMarkupId = outputMarkupId,
        outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
        behaviors = behaviors,
        validators = validators
    )
)

fun <C : Component> MarkupContainer.selectFormGroup(
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

fun <C : Component> MarkupContainer.selectFormGroup(
    id: String,
    useFormComponentLabel: Boolean? = null,
    group: (String) -> FormGroup = { KFormGroup(id = it, useFormComponentLabel = useFormComponentLabel) },
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    behavior: Behavior,
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
            behavior = behavior,
            field = field
        )
    )

fun <C : Component> MarkupContainer.textAreaGroup(
    id: String,
    useFormComponentLabel: Boolean? = null,
    group: (String) -> FormGroup = { KFormGroup(id = it, useFormComponentLabel = useFormComponentLabel) },
    field: (String) -> C,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    behaviors: List<Behavior>? = null
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
        behaviors = behaviors
    )
)

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

fun MarkupContainer.formGroup(
    id: String,
    label: IModel<String> = "".model(),
    help: IModel<String> = "".model(),
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    useFormComponentLabel: Boolean? = null,
    behaviors: List<Behavior>? = null
) = q(
    KFormGroup(
        id = id,
        label = label,
        help = help,
        outputMarkupId = outputMarkupId,
        outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
        visible = visible,
        enabled = enabled,
        useFormComponentLabel = useFormComponentLabel,
        behaviors = behaviors
    )
)

fun <C: Component> MarkupContainer.inputFormGroup(
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
        InputFormGroup(
            id = id,
            useFormComponentLabel = useFormComponentLabel,
            group = group,
            field = field,
            outputMarkupId = outputMarkupId,
            outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
            visible = visible,
            enabled = enabled,
            behaviors = behaviors
        )
    )

fun <C: Component> MarkupContainer.inputFormGroup(
    id: String,
    useFormComponentLabel: Boolean? = null,
    group: (String) -> FormGroup = { KFormGroup(id = it, useFormComponentLabel = useFormComponentLabel) },
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    behavior: Behavior,
    field: (String) -> C
) =
    q(
        InputFormGroup(
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
    )

fun <C: Component> MarkupContainer.fileFormGroup(
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
        FileFormGroup(
            id = id,
            useFormComponentLabel = useFormComponentLabel,
            group = group,
            field = field,
            outputMarkupId = outputMarkupId,
            outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
            visible = visible,
            enabled = enabled,
            behaviors = behaviors
        )
    )
