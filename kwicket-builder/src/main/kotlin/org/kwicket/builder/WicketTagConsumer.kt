package org.kwicket.builder

import kotlinx.html.Entities
import kotlinx.html.Tag
import kotlinx.html.TagConsumer
import kotlinx.html.Unsafe
import kotlinx.html.consumers.onFinalizeMap
import kotlinx.html.stream.HTMLStreamBuilder
import org.apache.wicket.Component
import org.apache.wicket.MarkupContainer
import org.w3c.dom.events.Event

data class RegionInfo(val markup: String, val rootComponentBuilders: List<ComponentBuilder>)

data class ComponentBuilder(
    val parent: ComponentBuilder? = null,
    val id: String,
    val builder: (String) -> Component
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
        // FIXME: what about this cast???
        children.forEach { it.addTo(c as MarkupContainer) }
    }

}

class IdIterator(private val prefix: String = "_c", private val postfix: String = "", private var seed: Int = 1) :
    Iterator<String> {
    override fun hasNext(): Boolean = true
    override fun next(): String = "$prefix${seed++}$postfix"
}

class WicketTagConsumer(
    private val downstream: TagConsumer<String>,
    private val idGenerator: Iterator<String> = IdIterator()
) :
    TagConsumer<RegionInfo> {

    private var roots: MutableList<ComponentBuilder> = mutableListOf()
    private var current: ComponentBuilder? = null

    override fun onTagStart(tag: Tag) {
        if (tag is WicketTag) {
            val wicketId = tag.id
            val id = wicketId ?: idGenerator.next()
            val b = tag.builder
            current = current?.let { it.add(id = id, builder = b) } ?: ComponentBuilder(id = id, builder = b)
            current?.let { if (it.parent == null) roots.add(it) }
            if (!tag.attributes.containsKey("wicket:id")) tag.attributes["wicket:id"] = id
        }
        downstream.onTagStart(tag)
    }

    override fun onTagAttributeChange(tag: Tag, attribute: String, value: String?) {}

    override fun onTagEvent(tag: Tag, event: String, value: (Event) -> Unit) {}

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

