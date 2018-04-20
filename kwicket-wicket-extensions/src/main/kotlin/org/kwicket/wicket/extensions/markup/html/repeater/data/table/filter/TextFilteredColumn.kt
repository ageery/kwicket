package org.kwicket.wicket.extensions.markup.html.repeater.data.table.filter

import org.apache.wicket.Component
import org.apache.wicket.markup.html.WebMarkupContainer
import org.apache.wicket.model.IModel
import org.kwicket.wicket.core.markup.html.KWebMarkupContainer
import org.kwicket.wicket.core.markup.html.form.KTextField
import org.kwicket.wicket.extensions.markup.html.repeater.data.table.FilteredLambdaColumn
import kotlin.reflect.KClass

open class TextFilteredColumn<T, S, F : Any>(
    displayModel: IModel<String>,
    model: IModel<F?>,
    type: KClass<F>? = null,
    sort: S?,
    function: (T) -> Any?,
    container: (String) -> WebMarkupContainer = { KWebMarkupContainer(id = it) },
    textField: (String, IModel<F?>, KClass<F>?) -> Component =
        { fieldId, fieldModel, fieldType ->
            KTextField(id = fieldId, model = fieldModel, type = fieldType)
        },
    rhs: (String) -> Component
) : FilteredLambdaColumn<T, S>(displayModel = displayModel, sort = sort, function = function,
    filter = { id, _ ->
        TextFieldSearchPanel(
            id = id,
            model = model,
            type = type,
            field = textField,
            rhs = rhs,
            container = container
        )
    })