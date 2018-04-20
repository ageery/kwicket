package org.kwicket.wicket.extensions.markup.html.repeater.data.table.filter

import org.apache.wicket.Component
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.AbstractFilter
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm
import org.kwicket.component.q

class KAbstractFilter(
    id: String,
    form: FilterForm<*>,
    component: (String, FilterForm<*>) -> Component
) : AbstractFilter(id, form) {

    init {
        q(component("panel", form))
    }

}