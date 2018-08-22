package org.kwicket.wicketstuff.select2

import org.apache.wicket.behavior.Behavior
import org.apache.wicket.model.IModel
import org.wicketstuff.select2.ChoiceProvider
import org.wicketstuff.select2.ISelect2Theme
import org.wicketstuff.select2.Select2Choice
import org.wicketstuff.select2.Select2MultiChoice

/**
 * Subclass of [Select2MultiChoice], a multi-item select dropdown form component, using named parameters and default
 * parameter values.
 *
 * @param T type of the items in the [Select2MultiChoice]
 * @param id Wicket id
 * @param model backing list model for the component
 * @param choiceProvider provides list of items for the dropdown
 * @param label label associated with the form field
 * @param visible whether the component is visible
 * @param escapeModelStrings whether to escape the strings in the model object
 * @param renderBodyOnly whether to only render the component tag(s) and not render the tag containing the
 * @param required whether it is required for an item to be selected in the dropdown
 * @param enabled whether the dropdown is enabled
 * @param width width of the dropdown
 * @param closeOnSelect whether to close the dropdown after the user has made a selection
 * @param allowClear whether to allow the user to unselect the item in the dropdown
 * @param placeholder text to show when no item has been selected in the dropdown
 * @param theme select2 theme
 * @param outputMarkupId whether to output a markup id for the dropdown
 * @param outputMarkupPlaceholderTag whether to output a placeholder tag if the dropdown is not initially visible
 * @param behaviors list of [Behavior]s to add to the dropdown
 */
open class KSelect2MultiChoice<T>(
    id: String,
    model: IModel<MutableCollection<T>>,
    choiceProvider: ChoiceProvider<T>,
    label: IModel<String>? = null,
    visible: Boolean? = null,
    escapeModelStrings: Boolean? = null,
    renderBodyOnly: Boolean? = null,
    required: Boolean? = null,
    enabled: Boolean? = null,
    width: String? = null,
    closeOnSelect: Boolean? = null,
    allowClear: Boolean? = null,
    placeholder: String? = "",
    theme: ISelect2Theme? = null,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    behaviors: List<Behavior>? = null
) : Select2MultiChoice<T>(id, model, choiceProvider) {

    init {
        init(
            width = width,
            closeOnSelect = closeOnSelect,
            allowClear = allowClear,
            placeholder = placeholder,
            theme = theme,
            outputMarkupId = outputMarkupId,
            outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
            behaviors = behaviors,
            label = label,
            required = required,
            enabled = enabled,
            visible = visible,
            renderBodyOnly = renderBodyOnly,
            escapeModelStrings = escapeModelStrings
        )
    }

}