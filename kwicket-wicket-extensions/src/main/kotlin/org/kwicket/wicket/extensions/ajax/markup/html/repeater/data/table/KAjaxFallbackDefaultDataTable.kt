package org.kwicket.wicket.extensions.ajax.markup.html.repeater.data.table

import org.apache.wicket.behavior.Behavior
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackDefaultDataTable
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractToolbar
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider
import org.kwicket.component.init

/**
 * [AjaxFallbackDefaultDataTable] with named parameters.
 *
 * @param T type of the data in a row of the table
 * @param S type of the sort used by columns in the table
 * @param id Wicket component id
 * @param columns list of columns in the table
 * @param dataProvider provides the row data for the table
 * @param rowsPerPage max number of rows to display in the table at a time
 * @param outputMarkupId whether to output an HTML id for the tag the component is in
 * @param outputMarkupPlaceholderTag whether to output a placeholder HTML tag if the component is not visible
 * @param topToolbars lambda for creating the top toolbars for the table
 * @param bottomToolbars lambda for creating the bottom toolbars for the table
 * @param behaviors list of [Behavior]s to add to the component
 */
open class KAjaxFallbackDefaultDataTable<T, S>(
    id: String,
    columns: List<IColumn<T, S>>,
    dataProvider: ISortableDataProvider<T, S>,
    rowsPerPage: Int,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    topToolbars: ((KAjaxFallbackDefaultDataTable<T, S>) -> List<AbstractToolbar>)? = null,
    bottomToolbars: ((KAjaxFallbackDefaultDataTable<T, S>) -> List<AbstractToolbar>)? = null,
    behaviors: List<Behavior>? = null
) : AjaxFallbackDefaultDataTable<T, S>(id, columns, dataProvider, rowsPerPage) {

    init {
        init(
            outputMarkupId = outputMarkupId,
            outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
            behaviors = behaviors
        )
        topToolbars?.let { it.invoke(this).forEach { toolbar -> addTopToolbar(toolbar) } }
        bottomToolbars?.let { it.invoke(this).forEach { toolbar -> addBottomToolbar(toolbar) } }
    }

}