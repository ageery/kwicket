package org.kwicket.model

import org.apache.wicket.model.LoadableDetachableModel
import java.io.Serializable

class IdentityLoadableDetachableModel<T, I : Serializable>(
    private val fromId: (I) -> T,
    val id: I
) : LoadableDetachableModel<T>() {

    constructor(
        fromId: (I) -> T,
        toId: (T) -> I,
        value: T
    ) : this(fromId = fromId, id = toId(value)) {
        this.value = value
    }

    override fun load(): T = fromId(id)

}