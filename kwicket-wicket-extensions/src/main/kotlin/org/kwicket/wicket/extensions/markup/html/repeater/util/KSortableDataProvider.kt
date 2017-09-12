package org.kwicket.wicket.extensions.markup.html.repeater.util

import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider
import org.apache.wicket.model.IModel
import org.kwicket.model.value

open class KSortableDataProvider<T, S>(val count: () -> Long,
                                     val items: (Long, Long, S?, Boolean) -> Sequence<T>,
                                     val modeler: (T) -> IModel<T>)
    : SortableDataProvider<T, S>() {

    override fun iterator(first: Long, count: Long): Iterator<T> = items(first, count, sort?.property, sort?.isAscending ?: true).iterator()
    override fun size(): Long = count()
    override fun model(value: T): IModel<T> = modeler(value)

}