package org.kwicket.wicket.extensions.markup.html.repeater.data.table

import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackHeadersToolbar
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortStateLocator
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable

open class KAjaxFallbackHeadersToolbar<S>(table: DataTable<*, S>, stateLocator: ISortStateLocator<S>)
    : AjaxFallbackHeadersToolbar<S>(table, stateLocator)