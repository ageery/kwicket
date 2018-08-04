package org.kwicket.wicket.extensions.markup.html.repeater.util

import java.io.Serializable

/**
 * Sort information for a [DataProviderSource].
 *
 * @param T type of the sort
 * @property field the sort
 * @property asc whether the sort is ascending
 */
data class Sort<out T>(val field: T, val asc: Boolean = true)

/**
 * Defines the source for a data provider.
 *
 * @param T the type of object the data provider works with
 * @param I the type of the identity of each object in the data provider
 * @param C the type of the criteria object
 * @param S the type of the sort of the data provider
 */
interface DataProviderSource<T, I : Serializable, in C, in S> : Serializable {

    /**
     * How to deflate or extract the identity value from an object in the data provider.
     *
     * @param value an object in the data provider
     * @return the identity of the object
     */
    fun toId(value: T): I

    /**
     * How to inflate or create an object in the data provider from an identity value.
     *
     * @param id the identity of an object in the data provider
     * @return an object in the data provider corresponding to the identity
     */
    fun fromId(id: I): T

    /**
     * Returns the count of the number of objects that match the criteria.
     *
     * @param criteria the search criteria to match
     * @return the number of objects that match the criteria
     */
    fun count(criteria: C? = null): Long

    /**
     * Returns the objects that match the [criteria].
     *
     * @param criteria search criteria to match
     * @param sorts the way the data backing the data provider should be sorted
     * @param offset the number of results to skip
     * @param limit the max number of results to return
     * @return list of items that match the [criteria]
     */
    fun find(
        criteria: C? = null,
        sorts: List<Sort<S>> = emptyList(),
        offset: Long? = null,
        limit: Long? = null
    ): Sequence<T>
}