package org.kwicket.model

import kotlinx.coroutines.experimental.CoroutineScope
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
interface AsyncModel<T> : IModel<T> {
    fun loadAsync()
}

/**
 * An implementation of [AsyncModel] using Kotlin coroutines.
 *
 * @param block the `suspend`able lambda for loading the value of the model
 * @param context the coroutine context the `suspend`able lambda will run in
 */
class AsyncLoadableDetachableModel<T>(
    private val block: suspend CoroutineScope.() -> T,
    private val context: (() -> CoroutineContext) = { DefaultDispatcher }
) : LoadableDetachableModel<T>(), AsyncModel<T> {

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

    override fun onDetach() {
        super.onDetach()
        _deferred = null
    }

}
