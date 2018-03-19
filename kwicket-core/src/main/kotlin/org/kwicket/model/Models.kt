package org.kwicket.model

import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.async
import org.apache.wicket.model.IModel
import org.apache.wicket.model.LoadableDetachableModel
import org.apache.wicket.model.Model
import org.apache.wicket.model.ResourceModel
import org.apache.wicket.model.util.ListModel
import java.io.Serializable
import kotlin.coroutines.experimental.createCoroutine
import kotlin.reflect.KProperty1

/**
 * Creates an [IModel] for a [Serializable] object.
 *
 * @param T type of the [Serializable] value
 * @receiver [Serializable] value of type [T]
 * @return [IModel] of type [T]
 */
fun <T : Serializable?> T.model(): IModel<T> = Model.of(this)

/**
 * Creates an [IModel] for a [List].
 *
 * @param T type of the items in the [List]
 * @receiver [List] containing values of type [T]
 * @return [IModel] of type [List] containing items of type [T]
 */
@Suppress("UNCHECKED_CAST")
fun <T, L : List<T>> L.listModel(): IModel<L> = ListModel(this) as IModel<L>

/**
 * Creates an [IModel] that uses the @receiver lambda to produce its value.
 *
 * @param T type returned by the producer
 * @receiver producer that returns objects of type [T]
 * @return [IModel] of type [T]
 */
fun <T> (() -> T).ldm(): IModel<T> = LoadableDetachableModel.of(this)

/**
 * Creates an [IModel] that uses the @receiver suspendable lambda to produce its value.
 *
 * @param T type returned by the producer
 * @receiver suspendable producer that returns objects of type [T]
 * @return [IModel] of type [T]
 */
fun <T> (suspend () -> T).ldm(): IModel<T> = SuspendableLoadableDetachableModel(block = this@ldm)

/**
 * Provides a readable/writable property with the name of "value" that returns the "object" of the [IModel],
 * without having to use the special Kotlin backtick syntax (since "object" is a reserved word in Kotlin).
 */
var <T> IModel<T>.value: T
    get() = `object`
    set(value) {
        `object` = value
    }

/**
 * Creates an [IModel] where the value is obtained from applying the [lambda] to the @receiver [IModel] value/object.
 *
 * @receiver [IModel] where the value is used when generating the value of the returned [IModel]
 * @param lambda the lambda to apply to the @receiver for creating a new [IModel]
 * @return [IModel] where the value comes from applying the [lambda] to the @receiver [IModel] value/object
 */
infix operator fun <M, T> IModel<M>.plus(lambda: (M) -> T): IModel<T> =
    object : LoadableDetachableModel<T>() {
        override fun load(): T = lambda(this@plus.value)
    }

/**
 * Creates a readable/writable [IModel] where the value is obtained from applying the [PropModel] to the @receiver [IModel].
 *
 * @receiver [IModel] where the value is used when generating the value of the returned [IModel]
 * @param chain [PropChain] used for getting a value from the @receiver [IModel]
 * @return readable/writable [IModel] where the value is obtained from applying the [PropModel] to the @receiver [IModel]
 */
infix operator fun <T> IModel<*>.plus(chain: PropChain<T>): IModel<T> = PropModel(this, chain)

/**
 * Creates a readable/writable [IModel] where the value is obtained from applying the [KProperty1] to the @receiver [IModel].
 *
 * @receiver [IModel] where the value is used when generating the value of the returned [IModel]
 * @param prop [KProperty1] used for getting a value from the @receiver [IModel]
 * @return readable/writable [IModel] where the value is obtained from applying the [KProperty1] to the @receiver [IModel]
 */
infix operator fun <M, T> IModel<M>.plus(prop: KProperty1<M, T>): IModel<T> = PropModel(this, prop)

/**
 * Creates a [ResourceModel] using the resource key and the optional default value.
 *
 * @param defaultValue String to use if no resource is found for the @receiver
 * @receiver resource key
 * @return [IModel<String>] where the value comes from a property file with the given resource key or the String
 * is the @receiver itself
 */
fun String.res(defaultValue: String = this): IModel<String> = ResourceModel(this, defaultValue)