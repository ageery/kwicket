package org.kwicket.wicket.extensions.ajax.markup.html.repeater.data.table

import kotlinx.coroutines.experimental.DefaultDispatcher
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import org.apache.wicket.Component
import org.apache.wicket.MarkupContainer
import org.apache.wicket.behavior.Behavior
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider
import org.apache.wicket.model.IModel
import org.slf4j.LoggerFactory
import kotlin.coroutines.experimental.CoroutineContext

private val LOGGER = LoggerFactory.getLogger(AsyncDataProvider::class.java)

interface AsyncDataProvider<T, S> : ISortableDataProvider<T, S> {
    fun startAsyncLoad(first: Long, size: Long)
    fun cancelAsyncLoad()
}

inline fun <T, U, R> suspend2(noinline block: suspend (T, U ) -> R): suspend (t: T, u: U) -> R = block
public inline fun <R> suspend1(noinline block: suspend () -> R): suspend () -> R = block

val xyz = suspend2 { start: Long, size: Long -> start + size }

open class KAsyncSortableDataProvider<T, S>(
    private val count: () -> Long,
    private val items: (Long, Long) -> Sequence<T>,
    private val modeler: (T) -> IModel<T>,
    private val context: (() -> CoroutineContext) = { DefaultDispatcher }
) : SortableDataProvider<T, S>(), AsyncDataProvider<T, S> {

    @Transient
    private var _deferredCount: Deferred<Long>? = null

    @Transient
    private var _deferredItems: Deferred<Sequence<T>>? = null

    private val deferredCount: Deferred<Long>
        get() {
            initDeferredCount()
            return _deferredCount!!
        }

    private fun deferredItems(first: Long, size: Long): Deferred<Sequence<T>> {
        initDeferredItems(first = first, size = size)
        return _deferredItems!!
    }

    private fun initDeferredCount() {
        _deferredCount.let {
            if (it == null) _deferredCount = async(context()) {
                LOGGER.info("before count")
                val x  = count()
                LOGGER.info("after count")
                x
            }
        }
    }

    private fun initDeferredItems(first: Long, size: Long) {
        _deferredItems.let {
            if (it == null) {
                _deferredItems = async(context()) {
                    items(first, size)
                }
            }
        }
    }

    override fun startAsyncLoad(first: Long, size: Long) {
        initDeferredItems(first, size)
    }

    override fun cancelAsyncLoad() {
        sequenceOf(_deferredCount, _deferredItems).forEach { deferred ->
            deferred?.let { if (it.isActive) it.cancel() }
        }
    }

    override fun iterator(first: Long, size: Long): Iterator<T> = runBlocking {
        deferredItems(first = first, size = size).await().iterator()
    }

    override fun size(): Long = runBlocking { deferredCount.await() }
    override fun model(value: T): IModel<T> = modeler(value)

    override fun detach() {
        _deferredCount?.let {
            it.cancel()
            _deferredCount = null
        }
        _deferredItems?.let {
            it.cancel()
            _deferredItems = null
        }

    }
}

class AsyncTableDataProviderLoadBehavior : Behavior() {

    private fun startAsync(c: Component) {
        if (c is DataTable<*, *>) {
            val dataProvider = c.dataProvider
            if (dataProvider is AsyncDataProvider<*, *>) {
                LOGGER.info("before startAsyncLoad - $c")
                dataProvider.startAsyncLoad(c.currentPage * c.itemsPerPage, c.itemsPerPage)
                LOGGER.info("after startAsyncLoad - $c")
            }
        }
    }

    override fun onConfigure(component: Component) {
        if (component.isVisibleInHierarchy) {
            startAsync(component)
            if (component is MarkupContainer) {
                component.visitChildren<Unit> { c, _ ->
                    startAsync(c)
                }
            }
        }
    }
}