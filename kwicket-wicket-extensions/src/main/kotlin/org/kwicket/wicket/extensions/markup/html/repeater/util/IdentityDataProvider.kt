package org.kwicket.wicket.extensions.markup.html.repeater.util

import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilterStateLocator
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam
import org.apache.wicket.model.IModel
import org.kwicket.model.IdentityLoadableDetachableModel
import org.kwicket.model.value
import java.io.Serializable

/**
 * Implementation of [IFilterStateLocator] and [ISortableDataProvider] using lambdas.
 *
 * @param T type of objects the data provider works with
 * @param I type of identity of the data provider objects
 * @param C type of the criteria used to filter items in the data provider
 * @param S type of the sort that the [DataProviderSource] works with
 * @param U type of the sort for the data provider
 * @property criteria model of the criteria backing the data provider
 * @param source [DataProviderSource] that provides the data for the data provider
 * @param sorter lambda that transforms a sort of the data provider into a list of sorts for the [DataProviderSource]
 * @param sort the way the data provider is initially sorted
 * @param asc whether the data provider sort is ascending
 */
open class IdentityDataProvider<T, I : Serializable, C, S, U>(
    private val criteria: IModel<C>,
    source: DataProviderSource<T, I, C, S>,
    sorter: (U) -> List<S>,
    sort: U? = null,
    asc: Boolean = true
) : IFilterStateLocator<C>, KSortableDataProvider<T, U>(
    count = { source.count(criteria.value) },
    items = { offset, limit, sorts, sortAsc ->
        source.find(
            criteria = criteria.value,
            offset = offset,
            limit = limit,
            sorts = sorts?.let { s -> sorter(s).map { Sort(it, sortAsc) } } ?: emptyList()
        )
    },
    modeler = { IdentityLoadableDetachableModel(value = it, fromId = source::fromId, toId = source::toId) }
) {

    init {
        this.sort = sort?.let { SortParam(it, asc) }
    }

    override fun setFilterState(state: C) {
        criteria.value = state
    }

    override fun getFilterState(): C = criteria.value
}