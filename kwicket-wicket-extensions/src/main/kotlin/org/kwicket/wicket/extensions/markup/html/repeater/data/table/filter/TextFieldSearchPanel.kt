package org.kwicket.wicket.extensions.markup.html.repeater.data.table.filter

import org.apache.wicket.Component
import org.apache.wicket.markup.html.WebMarkupContainer
import org.apache.wicket.model.IModel
import org.kwicket.component.q
import org.kwicket.wicket.core.markup.html.panel.KPanel
import kotlin.reflect.KClass

/**
 * Text search field.
 *
 * @param T type of the model for the search field
 * @param id Wicket component id
 * @param model backing model for the search field
 * @param type type of the model backing the search field
 * @param field lambda to create the text field portion of the search panel
 * @param rhs component (e.g., button) to add to the right of the field
 * @param container the panel the field and rhs components are in
 */
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