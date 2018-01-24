package org.kwicket.builder

import kotlinx.html.Entities
import kotlinx.html.Tag
import kotlinx.html.TagConsumer
import kotlinx.html.Unsafe
import kotlinx.html.consumers.delayed
import kotlinx.html.consumers.onFinalizeMap
import kotlinx.html.stream.HTMLStreamBuilder
import org.apache.wicket.Component
import org.apache.wicket.MarkupContainer
import org.w3c.dom.events.Event

data class ComponentBuilder(val parent: ComponentBuilder? = null, val id: String, val builder: (String) -> Component) {

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

data class RegionInfo(val markup: String, val rootComponentBuilders: List<ComponentBuilder>)

class WicketTagConsumer(val downstream: TagConsumer<String>) : TagConsumer<RegionInfo> {

    private var roots: MutableList<ComponentBuilder> = mutableListOf()
    private var current: ComponentBuilder? = null

    override fun onTagStart(tag: Tag) {
        downstream.onTagStart(tag)
        if (tag is WicketTag) {
            if (tag.id != null && tag.builder != null) {
                val cb = current?.let { it.add(id = tag.id!!, builder = tag.builder!!) } ?: ComponentBuilder(
                    id = tag.id!!,
                    builder = tag.builder!!
                )
                current = cb
                if (cb.parent == null) roots.add(cb)
            }
        }
    }

    override fun onTagAttributeChange(tag: Tag, attribute: String, value: String?) {
        throw UnsupportedOperationException("tag attribute can't be changed as it was already written to the stream. Use with DelayedConsumer to be able to modify attributes")
    }

    override fun onTagEvent(tag: Tag, event: String, value: (Event) -> Unit) {
        throw UnsupportedOperationException("you can't assign lambda event handler when building text")
    }

    override fun onTagEnd(tag: Tag) {
        downstream.onTagEnd(tag)
        if (tag is WicketTag) {
            current = current?.parent
        }
    }

    override fun onTagContent(content: CharSequence) {
        downstream.onTagContent(content)
    }

    override fun onTagContentEntity(entity: Entities) {
        downstream.onTagContentEntity(entity)
    }

    override fun finalize(): RegionInfo = RegionInfo(markup = downstream.finalize(), rootComponentBuilders = roots)

    override fun onTagContentUnsafe(block: Unsafe.() -> Unit) {
        downstream.onTagContentUnsafe(block)
    }
}

fun region(): TagConsumer<RegionInfo> = WicketTagConsumer(
    downstream = HTMLStreamBuilder(out = StringBuilder(), prettyPrint = false)
        .onFinalizeMap { sb, _ -> sb.toString() })
    .delayed()


