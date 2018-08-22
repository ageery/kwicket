package org.kwicket.wicket.extensions.markup.html.repeater.data.table.filter

import org.apache.wicket.Component
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.AbstractFilter
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm
import org.kwicket.component.q

/**
 * [AbstractFilter] with named parameters.
 *
 * @param id Wicket component id
 * @param form [FilterForm] associated with the filter
 * @param component lambda to create the filter component
 */
open class KAbstractFilter(
    id: String,
    form: FilterForm<*>,
    component: (String, FilterForm<*>) -> Component
) : AbstractFilter(id, form) {

    init {
        q(component("panel", form))
    }

}