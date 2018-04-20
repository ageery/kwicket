package org.kwicket.wicket.extensions.markup.html.repeater.data.table

import org.apache.wicket.Component
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilteredColumn
import org.apache.wicket.model.IModel

// FIXME: is there a better name than "function" -- maybe display or displayFunction or label?
open class FilteredLambdaColumn<T, S>(
    displayModel: IModel<String>,
    sort: S? = null,
    function: (T) -> Any?,
    val filter: (String, FilterForm<*>) -> Component
) : KLambdaColumn<T, S>(displayModel = displayModel, sort = sort, function = function), IFilteredColumn<T, S> {

    override fun getFilter(componentId: String, form: FilterForm<*>): Component = filter(componentId, form)
}