package org.kwicket.agilecoders.wicket.extensions

import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.datetime.DatetimePickerConfig
import org.apache.wicket.Component
import org.apache.wicket.behavior.Behavior
import org.apache.wicket.model.IModel
import org.kwicket.agilecoders.inputFormGroup
import org.kwicket.agilecoders.wicket.core.ajax.markup.html.bootstrap.form.InputFormGroup
import org.kwicket.agilecoders.wicket.extensions.markup.html.bootstrap.form.datetime.KDatetimePickerWithIcon
import java.time.LocalDate

fun datetimePickerWithIcon(
    model: IModel<LocalDate?>,
    label: IModel<String>? = null,
    required: Boolean? = null,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    escapeModelStrings: Boolean? = null,
    renderBodyOnly: Boolean? = null,
    behaviors: List<Behavior>? = null,
    format: String? = null,
    useCurrent: Boolean? = null,
    calendarWeeks: Boolean? = null,
    stepping: Int? = null,
    minDate: LocalDate? = null,
    maxDate: LocalDate? = null,
    defaultDate: LocalDate? = null,
    viewMode: DatetimePickerConfig.ViewModeType? = null,
    locale: String? = null,
    showTodayButton: Boolean? = null,
    showClose: Boolean? = null,
    collapse: Boolean? = null,
    sideBySide: Boolean? = null,
    useStrict: Boolean? = null
): (String) -> Component = {
    KDatetimePickerWithIcon(
        id = it,
        label = label,
        required = required,
        model = model,
        outputMarkupId = outputMarkupId,
        outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
        visible = visible,
        enabled = enabled,
        escapeModelStrings = escapeModelStrings,
        renderBodyOnly = renderBodyOnly,
        behaviors = behaviors,
        format = format,
        useCurrent = useCurrent,
        calendarWeeks = calendarWeeks,
        stepping = stepping,
        minDate = minDate,
        maxDate = maxDate,
        defaultDate = defaultDate,
        viewMode = viewMode,
        locale = locale,
        showTodayButton = showTodayButton,
        showClose = showClose,
        collapse = collapse,
        sideBySide = sideBySide,
        useStrict = useStrict
    )
}


fun datetimePickerWithIconFormGroup(
    model: IModel<LocalDate?>,
    label: IModel<String>? = null,
    required: Boolean? = null,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    escapeModelStrings: Boolean? = null,
    renderBodyOnly: Boolean? = null,
    behaviors: List<Behavior>? = null,
    format: String? = null,
    useCurrent: Boolean? = null,
    calendarWeeks: Boolean? = null,
    stepping: Int? = null,
    minDate: LocalDate? = null,
    maxDate: LocalDate? = null,
    defaultDate: LocalDate? = null,
    viewMode: DatetimePickerConfig.ViewModeType? = null,
    locale: String? = null,
    showTodayButton: Boolean? = null,
    showClose: Boolean? = null,
    collapse: Boolean? = null,
    sideBySide: Boolean? = null,
    useStrict: Boolean? = null
)
        : (String) -> InputFormGroup = inputFormGroup(
    field = datetimePickerWithIcon(
        model = model,
        required = required,
        label = label,
        outputMarkupId = outputMarkupId,
        outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
        visible = visible,
        enabled = enabled,
        escapeModelStrings = escapeModelStrings,
        renderBodyOnly = renderBodyOnly,
        behaviors = behaviors,
        format = format,
        useCurrent = useCurrent,
        calendarWeeks = calendarWeeks,
        stepping = stepping,
        minDate = minDate,
        maxDate = maxDate,
        defaultDate = defaultDate,
        viewMode = viewMode,
        locale = locale,
        showTodayButton = showTodayButton,
        showClose = showClose,
        collapse = collapse,
        sideBySide = sideBySide,
        useStrict = useStrict
    )
)