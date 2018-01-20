package org.kwicket.model

import kotlinx.coroutines.experimental.DefaultDispatcher
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import org.apache.wicket.model.IModel
import org.apache.wicket.model.LoadableDetachableModel
import kotlin.coroutines.experimental.CoroutineContext

/**
 * An extension of [IModel] that adds a method to start the load of the model's value asynchronously.
 */
interface IAsyncModel<T> : IModel<T> {
    fun loadAsync()
}

/**
 * An implementation of [IAsyncModel] using Kotlin coroutines.
 *
 * @param block the `suspend`able lambda for loading the value of the model
 * @param context the coroutine context the `suspend`able lambda will run in
 */
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
