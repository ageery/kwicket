package org.kwicket.model

import kotlinx.coroutines.experimental.DefaultDispatcher
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import org.apache.wicket.model.IModel
import org.apache.wicket.model.LoadableDetachableModel
import kotlin.coroutines.experimental.CoroutineContext

interface IAsyncModel<T> : IModel<T> {
    fun loadAsync(context: CoroutineContext = DefaultDispatcher): Deferred<T>
}

class AsyncModel<T>(private val block: suspend () -> T,
                    private val context: (() -> CoroutineContext)? = null) : LoadableDetachableModel<T>(), IAsyncModel<T> {

    @Transient
    private var deferred: Deferred<T>? = null

    override fun load(): T = runBlocking { loadAsync().await() }

    override fun loadAsync(context: CoroutineContext): Deferred<T> = deferred?.let { it }
                ?: async(this.context?.let { it() } ?: context) { block() }.let { deferred = it; it }

}
