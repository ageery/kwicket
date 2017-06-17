package org.kwicket.wicket.extensions.markup.html.repeater.util

import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider

abstract class KSortableDataProvider<T, S>(property: S, order: SortOrder) : SortableDataProvider<T, S>() {

    init {
        setSort(property, order)
    }

}