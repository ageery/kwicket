package org.kwicket.wicketstuff.select2

import org.apache.wicket.Component
import org.apache.wicket.model.IModel
import org.kwicket.wicket.extensions.markup.html.repeater.data.table.FilteredLambdaColumn
import org.wicketstuff.select2.ChoiceProvider

open class Select2FilteredColumn<T, S, F>(
    displayModel: IModel<String>,
    model: IModel<F?>,
    sort: S? = null,
    function: (T) -> Any?,
    choiceProvider: ChoiceProvider<F>,
    select2: (Component, String, IModel<F?>, ChoiceProvider<F>) -> Component
) : FilteredLambdaColumn<T, S>(displayModel = displayModel,
    sort = sort,
    function = function,
    filter = { id, _ ->
        Select2ChoiceFilterPanel(
            id = id,
            choiceProvider = choiceProvider,
            model = model,
            select2 = select2
        )
    })