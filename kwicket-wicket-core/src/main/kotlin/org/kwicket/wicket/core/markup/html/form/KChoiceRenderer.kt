package org.kwicket.wicket.core.markup.html.form

import org.apache.wicket.markup.html.form.IChoiceRenderer
import org.apache.wicket.model.IModel

/**
 * [IChoiceRenderer] implementation using lambdas.
 *
 * @param T type of the choices
 * @property toDisplay lambda for converting a choice into a string
 * @property toIdValue lambda for converting a choice into a unique string identifier
 * @property toObject lambda for converting a unique string identifier to a choice
 */
open class KChoiceRenderer<T>(
    private val toDisplay: (T) -> Any,
    private val toIdValue: (T, Int) -> String,
    private val toObject: (String, IModel<out MutableList<out T>>) -> T
) : IChoiceRenderer<T> {

    override fun getObject(id: String, choices: IModel<out MutableList<out T>>): T = toObject(id, choices)
    override fun getDisplayValue(value: T): Any = toDisplay(value)
    override fun getIdValue(value: T, index: Int): String = toIdValue(value, index)

}