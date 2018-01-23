package org.kwicket.wicket.core.util.convert.converter

import org.apache.wicket.util.convert.IConverter
import java.util.*

class KConverter<T>(
    val toString: (T, Locale) -> String,
    val toObject: (String, Locale) -> T = { _, _ -> throw NotImplementedError() }
) : IConverter<T> {

    override fun convertToObject(value: String, locale: Locale): T = toObject(value, locale)
    override fun convertToString(value: T, locale: Locale): String = toString(value, locale)

}