package org.kwicket.model

import kotlinx.coroutines.experimental.DefaultDispatcher
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import org.apache.wicket.model.IModel
import org.apache.wicket.model.LoadableDetachableModel
import kotlin.coroutines.experimental.CoroutineContext

interface IAsyncModel<T> : IModel<T> {
    fun loadAsync()
}

class AsyncModel<T>(
    private val block: suspend () -> T,
    private val context: (() -> CoroutineContext) = { DefaultDispatcher }
) : LoadableDetachableModel<T>(), IAsyncModel<T> {

    @Transient
    private var _deferred: Deferred<T>? = null

    private val deferred: Deferred<T>
        get() {
            initDeferred()
            return _deferred!!
        }

    private fun initDeferred() {
        _deferred.let {
            if (it == null) _deferred = async(context()) { block() }
        }
    }

    override fun load(): T = runBlocking { deferred.await() }

    override fun loadAsync() = initDeferred()

}
