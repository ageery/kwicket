package org.kwicket.wicketstuff.select2

/**
 * Simple [ChoiceProvider] for enums.
 *
 * @param T enum type of the items in the choice provider
 * @param toDisplayValue how to get a display representation from an item in the choice provider; by default, the enum
 * name is used
 * @param toIdValue how to get a unique string value from an item in the choice provider, by default, the enum name is
 * used
 * @param idToValue how to get an item in the choice provider from a unique string identifier; the `Enum.valueOf`
 * static method works well
 * @param allChoices how to get the list of all values for the choice provider; the `Enum.values` static method works
 * well
 */
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