package org.kwicket.model

import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import org.apache.wicket.model.IModel
import org.apache.wicket.model.LoadableDetachableModel

interface IAsyncModel<T> : IModel<T> {
    fun loadAsync(): Deferred<T>
}

class AsyncModel<T>(private val block: suspend () -> T) : LoadableDetachableModel<T>(), IAsyncModel<T> {

    @Transient
    private var deferred: Deferred<T>? = null

    override fun load(): T = runBlocking { loadAsync().await() }

    override fun loadAsync(): Deferred<T> = deferred?.let { it }
                ?: async { block() }.let { deferred = it; it }

}
