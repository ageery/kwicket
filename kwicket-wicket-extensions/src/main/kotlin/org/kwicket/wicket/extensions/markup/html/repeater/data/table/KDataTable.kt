package org.kwicket.wicket.extensions.markup.html.repeater.data.table

import org.apache.wicket.behavior.Behavior
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractToolbar
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider
import org.kwicket.component.init

open class KDataTable<T, S>(id: String,
                      columns: List<IColumn<T, S>>,
                      dataProvider: ISortableDataProvider<T, S>,
                      rowsPerPage: Int,
                      outputMarkupId: Boolean? = null,
                      outputMarkupPlaceholderTag: Boolean? = null,
                      topToolbars: ((KDataTable<T, S>) -> List<AbstractToolbar>)? = null,
                      bottomToolbars: ((KDataTable<T, S>) -> List<AbstractToolbar>)? = null,
                      vararg behaviors: Behavior)
    : DataTable<T, S>(id, columns, dataProvider, rowsPerPage.toLong()) {

    init {
        init(outputMarkupId = outputMarkupId,
                outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
                behaviors = *behaviors)
        topToolbars?.let { it.invoke(this).forEach { toolbar -> addTopToolbar(toolbar) } }
        bottomToolbars?.let { it.invoke(this).forEach { toolbar -> addBottomToolbar(toolbar) } }
    }

}
