package org.kwicket.wicketstuff.select2

import org.apache.wicket.Component
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator
import org.apache.wicket.markup.repeater.Item
import org.apache.wicket.model.IModel
import org.kwicket.wicket.extensions.markup.html.repeater.data.table.FilteredLambdaColumn
import org.wicketstuff.select2.ChoiceProvider

/**
 * [FilteredLambdaColumn] where the filter is a select2 dropdown.
 *
 * @param T type of item that makes up a row in the table
 * @param S type of the sort for the table
 * @param F type of the filter field for the column
 * @param displayModel model of the column header
 * @param model backing model for the filter dropdown
 * @param sort optional sort for the column
 * @param function lambda for producing a display value for the column
 * @param choiceProvider choice provider for the filter dropdown
 * @param select2 lambda for creating the select2 dropdown
 * @param populateItem lambda for populating a column cell
 */
open class Select2FilteredColumn<T, S, F>(
    displayModel: IModel<String>,
    model: IModel<F?>,
    sort: S? = null,
    function: (T) -> Any?,
    choiceProvider: ChoiceProvider<F>,
    select2: (Component, String, IModel<F?>, ChoiceProvider<F>) -> Component,
    cssClasses: List<String>? = null,
    populateItem: ((Item<ICellPopulator<T>>, String, IModel<T>, IModel<*>) -> Unit)? = null
) : FilteredLambdaColumn<T, S>(
    displayModel = displayModel,
    sort = sort,
    function = function,
    populateItem = populateItem,
    cssClasses = cssClasses,
    filter = { id, _ ->
        Select2ChoiceFilterPanel(
            id = id,
            choiceProvider = choiceProvider,
            model = model,
            select2 = select2
        )
    })