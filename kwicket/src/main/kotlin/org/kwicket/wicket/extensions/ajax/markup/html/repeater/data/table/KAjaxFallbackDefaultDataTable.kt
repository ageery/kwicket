package org.kwicket.wicket.extensions.ajax.markup.html.repeater.data.table

import org.apache.wicket.behavior.Behavior
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackDefaultDataTable
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider
import org.kwicket.component.initComponent

class KAjaxFallbackDefaultDataTable<T, S>(id: String,
                                          columns: List<IColumn<T, S>>,
                                          dataProvider: ISortableDataProvider<T, S>,
                                          rowsPerPage: Int,
                                          outputMarkupId: Boolean? = null,
                                          outputMarkupPlaceholderTag: Boolean? = null,
                                          vararg behaviors: Behavior)
    : AjaxFallbackDefaultDataTable<T, S>(id, columns, dataProvider, rowsPerPage) {

    init {
        initComponent(outputMarkupId = outputMarkupId,
                outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
                behaviors = *behaviors)
    }

}