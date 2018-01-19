package org.kwicket.model

import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import org.apache.wicket.model.IModel
import org.apache.wicket.model.LoadableDetachableModel
import java.io.Serializable

interface IAsync : Serializable {
    suspend fun loadAsync()
}

interface IAsyncModel<T> : IModel<T>, IAsync

class AsyncBatchLoader : Serializable {

    private val models: MutableSet<IAsync> = mutableSetOf()

    fun register(async: IAsync) {
        models.add(async)
    }

    fun loadAll() {
        runBlocking {
            models.map { launch { it.loadAsync() } }
                    .forEach { it.join() }
        }
    }

}

class AsyncModel<T>(private val loader: AsyncBatchLoader,
                    private val block: suspend () -> T)
    : LoadableDetachableModel<T>(), IAsyncModel<T> {

    init {
        loader.register(this)
    }

    override fun load(): T {
        loader.loadAll()
        return value
    }

    override suspend fun loadAsync() {
        if (!isAttached) {
            value = block()
        }
    }

}
