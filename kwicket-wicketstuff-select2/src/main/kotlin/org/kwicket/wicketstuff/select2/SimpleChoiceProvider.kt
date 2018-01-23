package org.kwicket.wicketstuff.select2

class SimpleChoiceProvider<T>(
    toDisplayValue: (T) -> String,
    toIdValue: (T) -> String,
    idToValue: (String) -> T,
    allChoices: () -> Sequence<T>,
    itemsPerPage: Int = 10
) : KChoiceProvider<T>(toDisplayValue = toDisplayValue,
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