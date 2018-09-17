package org.kwicket.wicket.core.util.convert.converter

import org.apache.wicket.util.convert.IConverter
import java.util.*

/**
 * Implementation of [IConverter] where the functionality is specified as lambdas.
 *
 * @param T the type of object to convert
 * @property toString converts an object of type [T] to a String representation
 * @property toObject optional String-to-[T] converter; this is only needed if the converter is needed to transform
 * Strings to objects
 */
open class KConverter<T>(
    val toString: (T, Locale) -> String,
    val toObject: (String, Locale) -> T = { _, _ -> throw NotImplementedError() }
) : IConverter<T> {
    override fun convertToObject(value: String, locale: Locale): T = toObject(value, locale)
    override fun convertToString(value: T, locale: Locale): String = toString(value, locale)
}