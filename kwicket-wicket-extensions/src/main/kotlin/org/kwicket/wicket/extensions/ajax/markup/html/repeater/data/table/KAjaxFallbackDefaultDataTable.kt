package org.kwicket.wicket.extensions.ajax.markup.html.repeater.data.table

import org.apache.wicket.behavior.Behavior
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackDefaultDataTable
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractToolbar
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider
import org.kwicket.component.init

class KAjaxFallbackDefaultDataTable<T, S>(id: String,
                                          columns: List<IColumn<T, S>>,
                                          dataProvider: ISortableDataProvider<T, S>,
                                          rowsPerPage: Int,
                                          outputMarkupId: Boolean? = null,
                                          outputMarkupPlaceholderTag: Boolean? = null,
                                          topToolbars: ((KAjaxFallbackDefaultDataTable<T, S>) -> List<AbstractToolbar>)? = null,
                                          bottomToolbars: ((KAjaxFallbackDefaultDataTable<T, S>) -> List<AbstractToolbar>)? = null,
                                          behaviors: List<Behavior>? = null)
    : AjaxFallbackDefaultDataTable<T, S>(id, columns, dataProvider, rowsPerPage) {

    init {
        init(outputMarkupId = outputMarkupId,
                outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
                behaviors = behaviors)
        topToolbars?.let { it.invoke(this).forEach { toolbar -> addTopToolbar(toolbar) } }
        bottomToolbars?.let { it.invoke(this).forEach { toolbar -> addBottomToolbar(toolbar) } }
    }

}