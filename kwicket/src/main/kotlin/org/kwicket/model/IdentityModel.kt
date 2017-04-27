package org.kwicket.model

import org.apache.wicket.model.LoadableDetachableModel

class IdentityModel<T, ID>(value: T?,
                           val idToValue: (ID) -> T,
                           val valueToId: (T) -> ID,
                           val id: ID = valueToId(value!!))
    : LoadableDetachableModel<T>(value) {

    override fun load(): T = idToValue(id)

}
