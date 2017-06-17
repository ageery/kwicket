package org.kwicket.wicket.extensions.markup.html.repeater.data.table

import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable
import org.apache.wicket.extensions.markup.html.repeater.data.table.NoRecordsToolbar
import org.kwicket.component.initComponent

class KNoRecordsToolbar(table: DataTable<*, *>,
                        visible: Boolean? = null)
    : NoRecordsToolbar(table) {

    init {
        initComponent(visible = visible)
    }

}
