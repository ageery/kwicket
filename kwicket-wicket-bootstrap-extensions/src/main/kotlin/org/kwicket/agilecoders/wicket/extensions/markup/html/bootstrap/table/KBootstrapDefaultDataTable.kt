package org.kwicket.agilecoders.wicket.extensions.markup.html.bootstrap.table

import de.agilecoders.wicket.extensions.markup.html.bootstrap.table.BootstrapDefaultDataTable
import org.apache.wicket.behavior.Behavior
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractToolbar
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider
import org.kwicket.component.init

open class KBootstrapDefaultDataTable<T, S>(
    id: String,
    columns: List<IColumn<T, S>>,
    dataProvider: ISortableDataProvider<T, S>,
    rowsPerPage: Int,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    topToolbars: ((BootstrapDefaultDataTable<T, S>) -> List<AbstractToolbar>)? = null,
    bottomToolbars: ((BootstrapDefaultDataTable<T, S>) -> List<AbstractToolbar>)? = null,
    vararg behaviors: Behavior
) : BootstrapDefaultDataTable<T, S>(id, columns, dataProvider, rowsPerPage.toLong()) {

    init {
        init(
            outputMarkupId = outputMarkupId,
            outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
            behaviors = *behaviors
        )
        topToolbars?.let { it.invoke(this).forEach { toolbar -> addTopToolbar(toolbar) } }
        bottomToolbars?.let { it.invoke(this).forEach { toolbar -> addBottomToolbar(toolbar) } }
striped()
    }

}
