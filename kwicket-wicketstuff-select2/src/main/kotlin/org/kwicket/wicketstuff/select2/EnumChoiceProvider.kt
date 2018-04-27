package org.kwicket.wicketstuff.select2

open class EnumChoiceProvider<T : Enum<*>>(
    toDisplayValue: (T) -> String = { it.name },
    toIdValue: (T) -> String = { it.name },
    idToValue: (String) -> T,
    allChoices: () -> Array<T>
) : SimpleChoiceProvider<T>(
    toDisplayValue = toDisplayValue,
    toIdValue = toIdValue,
    idToValue = idToValue,
    allChoices = { allChoices().asSequence() }
)