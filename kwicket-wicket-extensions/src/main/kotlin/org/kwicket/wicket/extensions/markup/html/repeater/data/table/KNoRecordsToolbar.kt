package org.kwicket.wicket.extensions.markup.html.repeater.data.table

import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable
import org.apache.wicket.extensions.markup.html.repeater.data.table.NoRecordsToolbar
import org.kwicket.component.init

/**
 * [NoRecordsToolbar] with named parameters.
 *
 * @param table the [DataTable] to add the toolbar to
 * @param visible whether the toolbar is visible
 */
open class KNoRecordsToolbar(table: DataTable<*, *>, visible: Boolean? = null) : NoRecordsToolbar(table) {

    init {
        init(visible = visible)
    }

}
