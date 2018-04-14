package org.kwicket.model

import org.apache.wicket.model.LoadableDetachableModel
import java.io.Serializable

class IdentityLoadableDetachableModel<T, I : Serializable>(
    private val fromId: (I) -> T,
    toId: (T) -> I,
    value: T
) : LoadableDetachableModel<T>() {

    private var id: I = toId(value)

    init {
        this.value = value
    }

    override fun load(): T = fromId(id)

}