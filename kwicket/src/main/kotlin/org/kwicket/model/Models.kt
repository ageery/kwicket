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
fun <T: Serializable?> T?.model(): IModel<T> = Model.of(this)

/**
 * Extension method that returns the [List] wrapped in an [IModel].
 *
 * @param T type of the items in the [List]
 * @receiver [List] containing values of type [T]
 * @return [IModel] of type [List] containing items of type [T]
 */
fun <T> List<T>.model(): IModel<List<T>> = ListModel(this)

/**
 * Extension method that returns the lambda producer wrapped in a [LoadableDetachableModel].
 *
 * @param T type returned by the lambda producer
 * @receiver lambda producer that returns objects of type [T]
 * @return [IModel] of type [() -> T?]
 */
fun <T> (() -> T?).ldm(): IModel<T> = LoadableDetachableModel.of(this)

/**
 * Extension property for [IModel] that provides a way of accessing and setting the object value of an [IModel]
 * without having to use the special Kotlin backtick syntax (since object is a reserved word in Kotlin).
 */
var <T> IModel<T>.value: T
    get() = `object`
    set(value) {
        `object` = value
    }