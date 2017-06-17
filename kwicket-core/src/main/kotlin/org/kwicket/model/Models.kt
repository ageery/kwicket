package org.kwicket.model

import org.apache.wicket.model.IModel
import org.apache.wicket.model.LoadableDetachableModel
import org.apache.wicket.model.Model
import org.apache.wicket.model.util.ListModel
import java.io.Serializable

/**
 * Extension method that returns the [Serializable] object wrapped in an [IModel].
 *
 * @param T type of the [Serializable] value
 * @receiver [Serializable] value of type [T]
 * @return [IModel] of type [T]
 * @sample [samples.org.kwicket.model.nonNullModelExample]
 * @sample [samples.org.kwicket.model.nullableModelExample]
 */
fun <T: Serializable?> T.model(): IModel<T> = Model.of(this)

/**
 * Extension method that returns the [List] wrapped in an [IModel].
 *
 * @param T type of the items in the [List]
 * @receiver [List] containing values of type [T]
 * @return [IModel] of type [List] containing items of type [T]
 */
fun <T> List<T>.listModel(): IModel<List<T>> = ListModel(this)

/**
 * Extension method that returns the producer wrapped in a [LoadableDetachableModel].
 *
 * @param T type returned by the producer
 * @receiver producer that returns objects of type [T]
 * @return [IModel] of type [() -> T?]
 */
fun <T> (() -> T).ldm(): IModel<T> = LoadableDetachableModel.of(this)

/**
 * Extension property for [IModel] that provides a way of accessing and setting the object value of an [IModel]
 * without having to use the special Kotlin backtick syntax (since object is a reserved word in Kotlin).
 */
var <T> IModel<T>.value: T
    get() = `object`
    set(value) {
        `object` = value
    }

/**
 * Extension method (and operator) that returns a new [IModel<T>] created from applying a (M) -> T lambda to an
 * [IModel<M>].
 */
infix operator fun <M, T> IModel<M>.plus(lambda: (M) -> T): IModel<T> = { this.value?.let { lambda.invoke(it)} ?: null as T }.ldm()

/**
 * Extension method (and operator) that returns a new [IModel<T>] created from applying a [PropChain<T>] to
 * a [IModel<M>]
 */
infix operator fun <T> IModel<*>.plus(chain: PropChain<T>): IModel<T> = PropModel(this, chain)