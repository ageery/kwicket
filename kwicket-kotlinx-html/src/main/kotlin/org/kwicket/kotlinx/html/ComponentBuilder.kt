package org.kwicket.kotlinx.html

import org.apache.wicket.Component
import org.apache.wicket.MarkupContainer

/**
 * Defines how to create a Wicket [Component] sub-tree.
 *
 * A definition for creating a [Component] sub-tree requires a Wicket id, a function for creating a Wicket
 * [Component] from an id and an optional parent [ComponentBuilder].
 */
data class ComponentBuilder(
    val parent: ComponentBuilder? = null,
    private val id: String,
    private val builder: (String) -> Component
) {

    private val children: MutableList<ComponentBuilder> = mutableListOf()

    fun add(id: String, builder: (String) -> Component): ComponentBuilder {
        val cb = ComponentBuilder(parent = this, id = id, builder = builder)
        children.add(cb)
        return cb
    }

    fun addTo(parent: MarkupContainer) {
        val c = builder(id)
        parent.add(c)
        children.forEach { it.addTo(c as MarkupContainer) }
    }

}