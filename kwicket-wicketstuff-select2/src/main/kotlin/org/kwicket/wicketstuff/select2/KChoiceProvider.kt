package org.kwicket.wicketstuff.select2

import org.wicketstuff.select2.ChoiceProvider
import org.wicketstuff.select2.Response

open class KChoiceProvider<T>(
    val toDisplayValue: (T) -> String,
    val toIdValue: (T) -> String,
    val toChoices: (MutableCollection<String>) -> MutableCollection<T>,
    val query: (String?, Int, Response<T>) -> Unit
) : ChoiceProvider<T>() {

    override fun getDisplayValue(obj: T): String = toDisplayValue(obj)
    override fun getIdValue(obj: T): String = toIdValue(obj)
    override fun toChoices(ids: MutableCollection<String>): MutableCollection<T> = toChoices.invoke(ids)
    override fun query(term: String?, page: Int, response: Response<T>) {
        query.invoke(term, page, response)
    }

}