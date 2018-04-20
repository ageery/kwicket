package org.kwicket.wicketstuff.select2

import org.apache.wicket.Component
import org.apache.wicket.model.IModel
import org.kwicket.component.q
import org.kwicket.wicket.core.markup.html.panel.KPanel
import org.wicketstuff.select2.ChoiceProvider

class Select2ChoiceFilterPanel<T>(
    id: String,
    model: IModel<T?>,
    choiceProvider: ChoiceProvider<T>,
    select2: (Component, String, IModel<T?>, ChoiceProvider<T>) -> Component
) : KPanel(id = id, model = model) {

    init {
        q(
            select2(this, "select", model, choiceProvider)
        )
    }

}