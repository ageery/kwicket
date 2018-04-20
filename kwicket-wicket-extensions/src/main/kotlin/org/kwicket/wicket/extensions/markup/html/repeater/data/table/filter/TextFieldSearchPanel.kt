package org.kwicket.wicket.extensions.markup.html.repeater.data.table.filter

import org.apache.wicket.Component
import org.apache.wicket.model.IModel
import org.kwicket.component.q
import org.kwicket.wicket.core.markup.html.panel.KPanel
import kotlin.reflect.KClass

open class TextFieldSearchPanel<T : Any>(
    id: String,
    model: IModel<T?>,
    type: KClass<T>? = null,
    field: (String, IModel<T?>, KClass<T>?) -> Component,
    button: (String) -> Component
) : KPanel(id = id, model = model) {

    init {
        q(field("field", model, type))
        q(button("button"))
    }

}