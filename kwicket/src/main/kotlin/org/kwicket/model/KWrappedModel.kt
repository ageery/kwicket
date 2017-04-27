package org.kwicket.model

import org.apache.wicket.WicketRuntimeException
import org.apache.wicket.model.IModel

class KWrappedModel<T, R>(val model: IModel<T>,
                          val getter: (T) -> R,
                          val setter: ((T, R) -> Unit)? = null)
    : IModel<R> {

    override fun getObject(): R {
        return getter(model.value)
    }

    override fun setObject(value: R) {
        setter?.invoke(model.value, value) ?: throw WicketRuntimeException("IModel#setObject not specified")
    }

    override fun detach() {
        model.detach()
    }

}
