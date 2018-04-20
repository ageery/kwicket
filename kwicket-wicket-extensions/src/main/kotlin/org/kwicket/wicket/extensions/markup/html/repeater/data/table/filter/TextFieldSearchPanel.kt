package org.kwicket.wicket.extensions.markup.html.repeater.data.table.filter

import org.apache.wicket.Component
import org.apache.wicket.markup.html.WebMarkupContainer
import org.apache.wicket.model.IModel
import org.kwicket.component.q
import org.kwicket.wicket.core.markup.html.panel.KPanel
import kotlin.reflect.KClass

open class TextFieldSearchPanel<T : Any>(
    id: String,
    model: IModel<T?>,
    type: KClass<T>? = null,
    field: (String, IModel<T?>, KClass<T>?) -> Component,
    rhs: (String) -> Component,
    container: (String) -> WebMarkupContainer
) : KPanel(id = id, model = model) {

    init {
        q(container("container"))
        q(field("field", model, type))
        q(rhs("rhs")).renderBodyOnly = true
    }

}