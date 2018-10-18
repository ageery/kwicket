package org.kwicket.wicketstuff.select2

import org.apache.wicket.behavior.Behavior
import org.apache.wicket.markup.html.form.FormComponent
import org.apache.wicket.model.IModel
import org.kwicket.component.init
import org.wicketstuff.select2.AbstractSelect2Choice
import org.wicketstuff.select2.ISelect2Theme

/**
 * Extension method for initializing a [AbstractSelect2Choice] component.
 *
 * @param label label associated with the form field
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
 * @param visible whether the component is visible
 * @param escapeModelStrings whether to escape the strings in the model object
 * @param renderBodyOnly whether to only render the component tag(s) and not render the tag containing the component
 */
fun <T> AbstractSelect2Choice<*, T>.init(
    width: String? = null,
    placeholder: String? = null,
    closeOnSelect: Boolean? = null,
    allowClear: Boolean? = null,
    theme: ISelect2Theme? = null,
    required: Boolean? = null,
    label: IModel<String>? = null,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    escapeModelStrings: Boolean? = null,
    renderBodyOnly: Boolean? = null,
    behaviors: List<Behavior>? = null
) {
    (this as FormComponent<T>).init(
        required = required,
        label = label,
        outputMarkupId = outputMarkupId,
        outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
        visible = visible,
        enabled = enabled,
        escapeModelStrings = escapeModelStrings,
        renderBodyOnly = renderBodyOnly,
        behaviors = behaviors
    )
    width?.let { settings.width = it }
    placeholder?.let { settings.placeholder = it }
    closeOnSelect?.let { settings.closeOnSelect = it }
    allowClear?.let { settings.allowClear = it }
    theme?.let { settings.theme = it }
}
