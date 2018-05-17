package org.kwicket.agilecoders.wicket.extensions.markup.html.bootstrap.form.datetime

import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.datetime.DatetimePickerConfig
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.datetime.DatetimePickerWithIcon
import org.apache.wicket.behavior.Behavior
import org.apache.wicket.extensions.markup.html.form.DateTextField
import org.apache.wicket.model.IModel
import org.kwicket.component.init
import org.kwicket.model.toDateModel
import java.sql.Date
import java.time.LocalDate

class KDatetimePickerWithIcon(
    id: String,
    model: IModel<LocalDate?>,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    escapeModelStrings: Boolean? = null,
    renderBodyOnly: Boolean? = null,
    val label: IModel<String>? = null,
    val required: Boolean? = null,
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
) : DatetimePickerWithIcon(id, toDateModel(model), null) {

    init {
        init(
            outputMarkupId = outputMarkupId,
            outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
            visible = visible,
            enabled = enabled,
            escapeModelStrings = escapeModelStrings,
            renderBodyOnly = renderBodyOnly,
            behaviors = behaviors
        )
        with(
            DatetimePickerConfig().apply {
                format?.let { withFormat(it) }
                useCurrent?.let { useCurrent(it) }
                calendarWeeks?.let { useCalendarWeeks(it) }
                stepping?.let { withMinuteStepping(it) }
                minDate?.let { withMinDate(Date.valueOf(it)) }
                maxDate?.let { withMaxDate(Date.valueOf(it)) }
                defaultDate?.let { withDefaultDate(Date.valueOf(it)) }
                viewMode?.let { useView(it) }
                locale?.let { useLocale(it) }
                showTodayButton?.let { setShowToday(it) }
                showClose?.let { setShowClose(it) }
                collapse?.let { setCollapse(it) }
                sideBySide?.let { useSideBySide(it) }
                useStrict?.let { useStrictParsing(it) }
            }
        )
    }

    override fun newInput(wicketId: String?, dateFormat: String?): DateTextField {
        val input = super.newInput(wicketId, dateFormat)
        input.init(
            label = label,
            required = required
        )
        return input
    }
}