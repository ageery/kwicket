package org.kwicket.wicket.extensions.markup.html.repeater.data.table

import org.apache.wicket.Component
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilteredColumn
import org.apache.wicket.markup.repeater.Item
import org.apache.wicket.model.IModel

/**
 * A filterable data table column.
 *
 * @param T type of rows in the the data table
 * @param S type of sort used in the data table
 * @param displayModel model of the column header label
 * @param sort optional sort for the column
 * @param function lambda for converting a table row to a column cell value
 * @property filter lambda for creating the column filter
 * @param header lambda for creating the table header
 * @param populateItem lambda for populating a column cell
 * @param cssClasses list of CSS classes to add to the column
 */
open class FilteredLambdaColumn<T, S>(
    displayModel: IModel<String>,
    sort: S? = null,
    function: (T) -> Any?,
    val filter: (String, FilterForm<*>) -> Component,
    header: ((String, IModel<String>) -> Component)? = null,
    populateItem: ((Item<ICellPopulator<T>>, String, IModel<T>, IModel<*>) -> Unit)? = null,
    cssClasses: List<String>? = null
) : KLambdaColumn<T, S>(displayModel = displayModel, sort = sort, function = function,
    header = header, populateItem = populateItem, cssClasses = cssClasses), IFilteredColumn<T, S> {

    override fun getFilter(componentId: String, form: FilterForm<*>): Component = filter(componentId, form)
}