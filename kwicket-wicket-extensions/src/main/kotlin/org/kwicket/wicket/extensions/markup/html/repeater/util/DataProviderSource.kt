package org.kwicket.wicket.extensions.markup.html.repeater.util

import java.io.Serializable

data class Sort<out T>(val field: T, val asc: Boolean = true)

interface DataProviderSource<T, I: Serializable, in C, in S> : Serializable {
    fun toId(value: T): I
    fun fromId(id: I): T
    fun count(criteria: C? = null): Long
    fun find(
        criteria: C? = null,
        sorts: List<Sort<S>> = emptyList(),
        offset: Long? = null,
        limit: Long? = null
    ): Sequence<T>
}