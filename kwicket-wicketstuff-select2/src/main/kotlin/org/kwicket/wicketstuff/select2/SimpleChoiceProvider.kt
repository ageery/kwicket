package org.kwicket.wicketstuff.select2

/**
 * [ChoiceProvider] implementation that provides a simple filtering query lambda from a list of all of the possible
 * choices.
 *
 * @param T type of the items in the choice provider
 * @param toDisplayValue how to map an item in the choice provider to a string for displaying
 * @param toIdValue how to convert an item in the choice provider to a unique string identifier
 * @param idToValue how to convert a unique string identifier to an item in the choice provider
 * @param allChoices how to get a list of all possible items for the choice provider
 * @param itemsPerPage the number of items in the choice provider to get at a time
 */
open class SimpleChoiceProvider<T>(
    toDisplayValue: (T) -> String,
    toIdValue: (T) -> String,
    idToValue: (String) -> T,
    allChoices: () -> Sequence<T>,
    itemsPerPage: Int = 10
) : KChoiceProvider<T>(
    toDisplayValue = toDisplayValue,
    toIdValue = toIdValue,
    toChoices = { ids -> ids.map { idToValue(it) }.toMutableList() },
    query = { term, page, response ->
        val list = allChoices()
            .filter { choice -> term?.let { toDisplayValue(choice).contains(ignoreCase = true, other = it) } != false }
            .drop(page * itemsPerPage)
            .take(itemsPerPage)
            .toList()
        response.addAll(list)
        response.hasMore = list.size == itemsPerPage
    })