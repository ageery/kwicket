package org.kwicket.agilecoders.wicket.extensions.markup.html.bootstrap.table.toolbars

import de.agilecoders.wicket.core.markup.html.bootstrap.navigation.BootstrapPagingNavigator
import de.agilecoders.wicket.core.markup.html.bootstrap.navigation.ajax.BootstrapAjaxPagingNavigator
import de.agilecoders.wicket.extensions.markup.html.bootstrap.table.toolbars.BootstrapNavigationToolbar
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable

open class BootstrapAjaxNavigationToolbar(
    table: DataTable<*, *>,
    size: BootstrapPagingNavigator.Size = BootstrapPagingNavigator.Size.Default
) : BootstrapNavigationToolbar(table, size) {

    override fun newPagingNavigator(
        navigatorId: String?,
        table: DataTable<*, *>?,
        size: BootstrapPagingNavigator.Size?
    ): BootstrapPagingNavigator = BootstrapAjaxPagingNavigator(navigatorId, table)

}