package org.kwicket.wicket.extensions.markup.html.repeater.data.table

import org.apache.wicket.behavior.Behavior
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortStateLocator
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractToolbar
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider
import org.kwicket.component.init

/**
 * [DataTable] with named parameters.
 *
 * @param T type of data in a row of the table
 * @param S type of the sort in the table
 * @param id Wicket component id
 * @param columns list of columns in the data table
 * @param dataProvider component that provides the data for the table
 * @param rowsPerPage number of rows to display per page
 * @param outputMarkupId whether to output a markup id for the table
 * @param outputMarkupPlaceholderTag whether to output a placeholder tag even if the table is not initially visible
 * @param topToolbars lambda to create the top toolbars
 * @param bottomToolbars lambda to create the bottom toolbars
 * @param behaviors list of behaviors to add to the table
 */
open class KDataTable<T, S>(
    id: String,
    columns: List<IColumn<T, S>>,
    dataProvider: ISortableDataProvider<T, S>,
    rowsPerPage: Int,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    topToolbars: ((KDataTable<T, S>, ISortStateLocator<S>) -> List<AbstractToolbar>)? = null,
    bottomToolbars: ((KDataTable<T, S>, ISortStateLocator<S>) -> List<AbstractToolbar>)? = null,
    behaviors: List<Behavior>? = null
) : DataTable<T, S>(id, columns, dataProvider, rowsPerPage.toLong()) {

    init {
        init(
            outputMarkupId = outputMarkupId,
            outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
            behaviors = behaviors
        )
        topToolbars?.let { it.invoke(this, dataProvider).forEach { toolbar -> addTopToolbar(toolbar) } }
        bottomToolbars?.let { it.invoke(this, dataProvider).forEach { toolbar -> addBottomToolbar(toolbar) } }
    }

}
