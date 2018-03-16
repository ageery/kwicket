package org.kwicket.wicket.core.markup.html.form

import org.apache.wicket.markup.html.form.Form
import org.apache.wicket.markup.html.form.IChoiceRenderer
import org.apache.wicket.model.IModel

/**
 * [IChoiceRenderer] implementation using lambdas.
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