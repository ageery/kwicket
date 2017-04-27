package org.kwicket.model

import org.apache.wicket.WicketRuntimeException
import org.apache.wicket.model.IModel
import java.io.Serializable
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KProperty1
import kotlin.reflect.jvm.javaGetter
import kotlin.reflect.jvm.javaMethod
import kotlin.reflect.jvm.jvmErasure

class KPropertyModel<T, S, R>(val model: IModel<T>, init: KPropBuilder.() -> KProperty1<S, R>) : IModel<String> {

    val getters: List<String>
    val builder: KPropBuilder = KPropBuilder()
    val setterName: String?
    //val lastGetter: (S) -> R
    //val lastSetter: ((S, R) -> Unit)?
    //val lastProp: KProperty1<S, R>

    init {
        //builder.props.map { { onConfigure -> it.getter.invoke(onConfigure) } }
        val lastProp = init(builder)
        setterName = if (lastProp is KMutableProperty1) lastProp.setter.javaMethod!!.name else null
        builder.getters.forEach(::println)
        getters = builder.getters
        println(setterName)
        //val lastGetter = { s: S -> lastProp.get(s) }
        //val lastSetter: ((S, R) -> Unit)? = if (lastProp is KMutableProperty1<S, R>) { s: S, r: R -> lastProp.set(s, r) } else null
    }

    override fun getObject(): String {
        return "test"
    }

//    var builder: KPropBuilder? = null
//
//    fun onConfigure(initFormComponent: KPropBuilder.() -> KProperty1<*, T>) {
//        val onConfigure = KPropBuilder()
//        val lastProp = initFormComponent(onConfigure)
//        builder = onConfigure
//    }
}

class KPropModel<T, R>(val model: IModel<T>, val props: PropChain<R>) : IModel<R> {
    override fun getObject(): R? = if (model.value == null) null else props.getValue(model.value)
    override fun setObject(value: R) = if (model.value != null) props.setValue(model.value, value) else throw WicketRuntimeException()
}

class SetterInfo<T>(val name: String, val type: Class<out T>) : Serializable {

    fun setValue(obj: Any, value: T) {
        val setter = obj::class.java.getMethod(name, type)
        setter.invoke(obj, value)
    }

}

// PropChain  { Person::address + Address::City }
class PropChain<T>(init: PropChainBuilder.() -> KProperty1<*, T>) : Serializable {

    val getters: List<String>
    val setter: SetterInfo<T>?

    private fun <V: Any?> xyz(obj: V, getters: List<String>): Any? {
        if (obj == null) {
            return null
        } else {
            var x: Any? = obj
            for (getter in getters) {
                if (x == null) {
                    break
                }
                val m = x::class.java.getMethod(getter)
                x = m.invoke(x)
            }
            return x as T?
        }
    }

    fun <V: Any?> getValue(obj: V): T? {
        if (obj == null) {
            return null
        } else {
            return xyz(obj, getters) as T?
        }
    }

    fun <V: Any?> setValue(obj: V, value: T) {
        if (setter != null && obj != null) {
            val x = xyz(obj, getters.subList(0, getters.size - 1))
            if (x != null) {
                setter.setValue(x, value)
            } else {
                throw WicketRuntimeException("Parent object is null")
            }
        } else {
            throw WicketRuntimeException("First object in chain is null or no setter")
        }
    }

    init {
        val builder = PropChainBuilder()
        val lastProp = init(builder)
        getters = builder.getterNamees.toList()
        if (lastProp is KMutableProperty1) {
            setter = SetterInfo(name = lastProp.setter.javaMethod!!.name,
                    type = lastProp.setter.parameters[1].type.jvmErasure.java as Class<out T>)
        } else {
            setter = null
        }
    }

}

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



class KPropBuilder : Serializable {

    var getters: MutableList<String> = mutableListOf()

    operator fun <T, R> KProperty1<T, R>.unaryPlus(): KProperty1<T, R> {
        getters.add(this.getter.javaMethod!!.name)
        return this
    }

    operator fun <T, S, R> KProperty1<T, S?>.plus(other: KProperty1<S, R>): KProperty1<S, R> {
        if (getters.isEmpty()) {
            getters.add(this.getter.javaMethod!!.name)
        }
        getters.add(other.getter.javaMethod!!.name)
        return other
    }

}

operator inline fun <A, B, C> ((A) -> B?).plus(crossinline other: (B) -> C?): ((A) -> C?) {
    return {
        val ab = this(it)
        if (ab != null) other(ab) else null
    }
}
