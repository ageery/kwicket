package org.kwicket.wicket.extensions.markup.html.repeater.data.table.filter

import org.apache.wicket.Component
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator
import org.apache.wicket.markup.html.WebMarkupContainer
import org.apache.wicket.markup.repeater.Item
import org.apache.wicket.model.IModel
import org.kwicket.wicket.core.markup.html.KWebMarkupContainer
import org.kwicket.wicket.core.markup.html.form.KTextField
import org.kwicket.wicket.extensions.markup.html.repeater.data.table.FilteredLambdaColumn
import kotlin.reflect.KClass

/**
 * Column filterable with a text field.
 *
 * @param T type of a row in the data table
 * @param S type of the sort for the data table
 * @param F type of the filter field
 * @param displayModel model for the table header label
 * @param model backing model for the filter field
 * @param type type of the model backing the filter field
 * @param sort the sort for the column
 * @param function lambda for creating an object to display in a column cell
 * @param container lambda for creating the container the field and rhs components are in
 * @param textField lambda for creating the search field
 * @param rhs lambda for creating the rhs of the search panel
 * @param header lambda for creating the column header
 * @param populateItem lambda for populating the
 */
open class TextFilteredColumn<T, S, F : Any>(
    displayModel: IModel<String>,
    model: IModel<F?>,
    type: KClass<F>? = null,
    sort: S?,
    function: (T) -> Any?,
    container: (String) -> WebMarkupContainer = { KWebMarkupContainer(id = it) },
    textField: (String, IModel<F?>, KClass<F>?) -> Component =
        { fieldId, fieldModel, fieldType ->
            KTextField(id = fieldId, model = fieldModel, type = fieldType)
        },
    rhs: (String) -> Component,
    header: ((String, IModel<String>) -> Component)? = null,
    populateItem: ((Item<ICellPopulator<T>>, String, IModel<T>, IModel<*>) -> Unit)? = null,
    cssClasses: List<String>? = null
) : FilteredLambdaColumn<T, S>(displayModel = displayModel, sort = sort, function = function,
    header = header, populateItem = populateItem, cssClasses = cssClasses,
    filter = { id, _ ->
        TextFieldSearchPanel(
            id = id,
            model = model,
            type = type,
            field = textField,
            rhs = rhs,
            container = container
        )
    })