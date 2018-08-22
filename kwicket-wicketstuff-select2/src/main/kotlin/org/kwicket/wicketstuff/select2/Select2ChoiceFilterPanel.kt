package org.kwicket.wicketstuff.select2

import org.apache.wicket.Component
import org.apache.wicket.model.IModel
import org.kwicket.component.q
import org.kwicket.wicket.core.markup.html.panel.KPanel
import org.wicketstuff.select2.ChoiceProvider

/**
 * Panel that provides the filter component for [Select2FilteredColumn].
 *
 * @param T type of the items in the dropdown
 * @param id Wicket component identifier
 * @param model backing model for the select2 dropdown
 * @param choiceProvider choice provider for the select2 dropdown
 * @param select2 lambda for creating a select3 dropdown component
 */
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