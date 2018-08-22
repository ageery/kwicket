package org.kwicket.wicketstuff.select2

import org.wicketstuff.select2.ChoiceProvider
import org.wicketstuff.select2.Response

/**
 * [ChoiceProvider] where the functionality is provided as constructor parameters.
 *
 * @param T the type of the items in the provider
 * @property toDisplayValue how to generate a display string for an item
 * @property toIdValue how to get a unique string id for an item
 * @property toChoices how to get a list of items from a list of unique string ids
 * @property query how to get a page of results given an optional search string, a page number and a [Response] to
 * put the items in
 */
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