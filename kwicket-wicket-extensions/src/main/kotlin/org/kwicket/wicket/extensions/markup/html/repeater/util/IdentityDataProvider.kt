package org.kwicket.wicket.extensions.markup.html.repeater.util

import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilterStateLocator
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam
import org.apache.wicket.model.IModel
import org.kwicket.model.IdentityLoadableDetachableModel
import org.kwicket.model.value
import java.io.Serializable

private fun <S, U> U.toSortInfo(asc: Boolean = true, sorter: (U) -> List<S>) =
    sorter.invoke(this).map { Sort(it, asc) }

open class IdentityDataProvider<T, I : Serializable, C, S, U>(
    private val criteria: IModel<C>,
    source: DataProviderSource<T, I, C, S>,
    sorter: (U) -> List<S>,
    sort: U? = null
) : IFilterStateLocator<C>, KSortableDataProvider<T, U>(
    count = { source.count(criteria.value) },
    items = { offset, limit, sorts, asc ->
        source.find(
            criteria = criteria.value,
            offset = offset,
            limit = limit,
            sorts = sorts?.toSortInfo<S, U>(asc = asc, sorter = sorter) ?: emptyList()
        )
    },
    modeler = { IdentityLoadableDetachableModel(value = it, fromId = source::fromId, toId = source::toId) }
) {

    init {
        this.sort = sort?.let { SortParam(it, true) }
    }

    override fun setFilterState(state: C) {
        criteria.value = state
    }

    override fun getFilterState(): C = criteria.value
}