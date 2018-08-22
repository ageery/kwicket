package org.kwicket.wicket.extensions.markup.html.repeater.data.table

import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackHeadersToolbar
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortStateLocator
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable

/**
 * [AjaxFallbackHeadersToolbar] with named parameters.
 *
 * @param S type of sort used in the table
 * @param table the [DataTable] the toolbar is being added to
 * @param stateLocator how to determine the sort state of the table
 */
open class KAjaxFallbackHeadersToolbar<S>(table: DataTable<*, S>, stateLocator: ISortStateLocator<S>) :
    AjaxFallbackHeadersToolbar<S>(table, stateLocator)