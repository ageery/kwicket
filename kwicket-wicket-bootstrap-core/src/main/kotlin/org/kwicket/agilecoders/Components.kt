package org.kwicket.agilecoders

import de.agilecoders.wicket.core.markup.html.bootstrap.form.InputBehavior
import org.apache.wicket.Component
import org.apache.wicket.behavior.Behavior
import org.apache.wicket.markup.html.form.FormComponent
import org.apache.wicket.model.IModel
import org.kwicket.agilecoders.wicket.core.ajax.markup.html.bootstrap.form.InputFormGroup
import org.kwicket.wicket.core.markup.html.form.KTextField
import kotlin.reflect.KClass

fun <T : Any> textField(
    model: IModel<T?>? = null,
    type: KClass<T>? = null,
    required: Boolean? = null,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    label: IModel<String>? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    behaviors: List<Behavior>? = null
): (String) -> FormComponent<T> = {
    KTextField(
        id = it,
        model = model,
        type = type,
        label = label,
        required = required,
        outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
        outputMarkupId = outputMarkupId,
        visible = visible,
        enabled = enabled,
        behaviors = behaviors?.let { it + InputBehavior() } ?: listOf(InputBehavior())
    )
}

fun inputFormGroup(field: (String) -> Component): (String) -> InputFormGroup<*> =
    { InputFormGroup(id = it, field = field) }

fun <T : Any> textFieldFormGroup(
    model: IModel<T?>? = null,
    type: KClass<T>? = null,
    required: Boolean? = null,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    label: IModel<String>? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    behaviors: List<Behavior>? = null
)
        : (String) -> InputFormGroup<*> = inputFormGroup(
    field = textField(
        model = model,
        type = type,
        label = label,
        required = required,
        outputMarkupId = outputMarkupId,
        outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
        visible = visible,
        enabled = enabled,
        behaviors = behaviors
    )
)