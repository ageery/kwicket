package org.kwicket.wicket.extensions.markup.html.form

import org.apache.wicket.behavior.Behavior
import org.apache.wicket.extensions.markup.html.form.DateTextField
import org.apache.wicket.model.IModel
import org.kwicket.component.init
import org.kwicket.model.toDateModel
import java.time.LocalDate

/**
 * [DateTextField] with named parameters and a [LocalDate] model.
 *
 * @param id Wicket component id
 * @param model backing model for the component
 * @param pattern the date pattern to use for parsing and rendering dates from and to strings
 * @param outputMarkupId whether to output an id in the markup for the component
 * @param outputMarkupPlaceholderTag whether to output a placeholder tag for the component if it is not initially
 * visible
 * @param visible whether the component is visible
 * @param enabled whether the component is enabled
 * @param escapeModelStrings whether the value of the model should have HTML tags escaped
 * @param renderBodyOnly whether the tag containing the component should not be rendered
 * @param behaviors list of [Behavior]s to add to the component
 */
open class KDateTextField(
    id: String,
    model: IModel<LocalDate?>,
    pattern: String,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    escapeModelStrings: Boolean? = null,
    renderBodyOnly: Boolean? = null,
    behaviors: List<Behavior>? = null
) : DateTextField(id, toDateModel(model), pattern) {

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
    }

}