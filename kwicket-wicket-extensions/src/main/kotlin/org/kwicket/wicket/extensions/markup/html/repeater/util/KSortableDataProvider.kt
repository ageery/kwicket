package org.kwicket.wicket.extensions.markup.html.repeater.util

import org.apache.wicket.extensions.markup.html.repeater.util.SortParam
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider
import org.apache.wicket.model.IModel

/**
 * Extension of [SortableDataProvider] where the implementations are provided by lambdas.
 *
 * @param T the type of object in the data provider
 * @param S the type of the sort for the data provider
 * @property count lambda that counts the number of items in the data provider
 * @property items lambda that returns a [Sequence] of items in the data provider; the lambda parameters are:
 * offset, limit, optional sort and whether the sort is ascending
 * @property modeler lambda that converts an item in the data provider into a model of an item in the data provider
 * @param initialSort how tht data provider is initially sorted
 */
open class KSortableDataProvider<T, S>(
    val count: () -> Long,
    val items: (Long, Long, S?, Boolean) -> Sequence<T>,
    val modeler: (T) -> IModel<T>,
    initialSort: SortParam<S>? = null
) : SortableDataProvider<T, S>() {

    init {
        initialSort?.let { sort = it }
    }

    /**
     * @param count lambda that counts the number of items in the data provider
     * @param items lambda that returns a [Sequence] of items in the data provider; the lambda parameters are:
     * offset, limit, optional sort and whether the sort is ascending
     * @param modeler lambda that converts an item in the data provider into a model of an item in the data provider
     * @param initialSort property on which to sort the data provider; the sort is ascending
     */
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