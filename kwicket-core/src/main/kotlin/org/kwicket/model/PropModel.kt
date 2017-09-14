package org.kwicket.model

import org.apache.wicket.WicketRuntimeException
import org.apache.wicket.model.IModel
import java.io.Serializable
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KProperty1
import kotlin.reflect.jvm.javaGetter
import kotlin.reflect.jvm.javaMethod
import kotlin.reflect.jvm.jvmErasure

/**
 * [IModel] that applies the [props] to the [model] for getting and setting the model's value.
 *
 * @param T type of the [IModel]'s value/object
 * @param model [IModel] whose value is
 * @param props [PropChain] that is applied to the [model] value to get the value of the [IModel]
 */
class PropModel<T>(val model: IModel<*>, private val props: PropChain<T>) : IModel<T> {

    constructor(model: IModel<*>, prop: KProperty1<*, T>) : this(model = model, props = PropChain { +prop })

    override fun getObject(): T = if (model.value == null) null as T else props.getValue(model.value)
    override fun setObject(value: T) = props.setValue(model.value, value)
}

/**
 * Represents a [Serializable] chain of properties.
 */
class PropChain<T>(init: PropChainBuilder.() -> KProperty1<*, T>) : Serializable {

    private val getters: List<String>
    private val setter: SetterInfo<T>?

    init {
        val builder = PropChainBuilder()
        val lastProp = init(builder)
        getters = builder.getterNamees.toList()

        @Suppress("UNCHECKED_CAST")
        setter = if (lastProp !is KMutableProperty1) null else
            SetterInfo(name = lastProp.setter.javaMethod!!.name,
                    type = lastProp.setter.parameters[1].type.jvmErasure.java as Class<out T>)
    }

    @Suppress("UNCHECKED_CAST")
    private fun computeValue(obj: Any?, getters: List<String>): T = getters.fold(obj, { value, getterName ->
        value?.let { it::class.java.getMethod(getterName).invoke(value) } }) as T

    fun getValue(obj: Any?): T = computeValue(obj, getters)

    fun setValue(obj: Any?, value: T) {
        if (setter != null) {
            val setterParentValue = computeValue(obj, getters.subList(0, getters.size - 1))
            if (setterParentValue != null) {
                setter.setValue(setterParentValue, value)
            } else {
                throw WicketRuntimeException("Parent object is null")
            }
        } else {
            throw WicketRuntimeException("There is no setter")
        }
    }

}

/**
 * Builder for creating a [PropChain] object.
 */
class PropChainBuilder {

    private var props: MutableList<KProperty1<*, *>> = mutableListOf()

    operator fun <T, R> KProperty1<T, R>.unaryPlus(): KProperty1<T, R> {
        props.add(this)
        return this
    }

    operator fun <T, S, R> KProperty1<T, R>.plus(other: KProperty1<S, R>): KProperty1<S, R> {
        if (props.isEmpty()) {
            props.add(this)
        }
        props.add(other)
        return other
    }

    val getterNamees: Sequence<String>
        get() = props.asSequence().map { it.javaGetter!!.name }

}

private class SetterInfo<T>(val name: String, val type: Class<out T>) : Serializable {
    fun setValue(obj: Any, value: T) {
        obj::class.java.getMethod(name, type).invoke(obj, value)
    }
}

