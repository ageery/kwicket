package org.kwicket.wicket.extensions.markup.html.repeater.data.table.filter

import org.apache.wicket.behavior.Behavior
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterToolbar
import org.kwicket.component.init

/**
 * [FilterToolbar] with named parameters.
 *
 * @param T type of data in a row in the data table
 * @param S type of the sort for the data table
 * @param C type of the model backing the [FilterForm] for the toolbar
 * @param table data table the toolbar is being added to
 * @param form [FilterForm] for the toolbar
 * @param visible whether the toolbar is visible
 * @param behaviors list of [Behavior]s to add to the toolbar
 */
open class KFilterToolbar<T, S, C>(
    table: DataTable<T, S>,
    form: FilterForm<C>,
    visible: Boolean? = null,
    behaviors: List<Behavior>? = null
) : FilterToolbar(table, form) {

    init {
        init(
            behaviors = behaviors,
            visible = visible
        )
    }

}
