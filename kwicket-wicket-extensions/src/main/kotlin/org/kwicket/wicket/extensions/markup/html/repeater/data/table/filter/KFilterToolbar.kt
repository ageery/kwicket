package org.kwicket.wicket.extensions.markup.html.repeater.data.table.filter

import org.apache.wicket.behavior.Behavior
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterToolbar
import org.kwicket.component.initComponent

class KFilterToolbar<T, S, C>(table: DataTable<T, S>,
                           form: FilterForm<C>,
                           visible: Boolean? = null,
                           vararg behaviors: Behavior)
    : FilterToolbar(table, form) {

    init {
        initComponent(behaviors = *behaviors,
                visible = visible)
    }

}
