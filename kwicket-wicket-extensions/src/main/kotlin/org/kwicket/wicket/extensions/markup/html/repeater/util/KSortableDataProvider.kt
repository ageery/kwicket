package org.kwicket.wicket.extensions.markup.html.repeater.util

import org.apache.wicket.extensions.markup.html.repeater.util.SortParam
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider
import org.apache.wicket.model.IModel

open class KSortableDataProvider<T, S>(
    val count: () -> Long,
    val items: (Long, Long, S?, Boolean) -> Sequence<T>,
    val modeler: (T) -> IModel<T>,
    initialSort: SortParam<S>? = null
) : SortableDataProvider<T, S>() {

    init {
        initialSort?.let { sort = it }
    }

    constructor(
        count: () -> Long,
        items: (Long, Long, S?, Boolean) -> Sequence<T>,
        modeler: (T) -> IModel<T>,
        initialSort: S
    ) : this(
        count = count, items = items, modeler = modeler, initialSort = initialSort?.let { SortParam(it, true) }
    )

    override fun iterator(first: Long, count: Long): Iterator<T> =
        items(first, count, sort?.property, sort?.isAscending != false).iterator()

    override fun size(): Long = count()
    override fun model(value: T): IModel<T> = modeler(value)

}