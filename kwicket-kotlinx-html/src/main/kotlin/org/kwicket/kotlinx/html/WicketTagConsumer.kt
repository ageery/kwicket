package org.kwicket.kotlinx.html

import kotlinx.html.Entities
import kotlinx.html.Tag
import kotlinx.html.TagConsumer
import kotlinx.html.Unsafe
import kotlinx.html.consumers.onFinalizeMap
import kotlinx.html.stream.HTMLStreamBuilder
import org.kwicket.wicketIdAttr
import org.w3c.dom.events.Event


private class IdIterator(
    private val prefix: String = "_c",
    private val postfix: String = "",
    private var seed: Int = 1
) :
    Iterator<String> {
    override fun hasNext(): Boolean = true
    override fun next(): String = "$prefix${seed++}$postfix"
}

private class WicketTagConsumer(
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
            current = current?.let { it.add(id = id, builder = b) } ?:
                    ComponentBuilder(id = id, builder = b)
            current?.let { if (it.parent == null) roots.add(it) }
            if (!tag.attributes.containsKey(wicketIdAttr)) tag.attributes[wicketIdAttr] = id
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

    override fun finalize(): RegionInfo =
        RegionInfo(markup = downstream.finalize(), rootComponentBuilders = roots)

    override fun onTagContentUnsafe(block: Unsafe.() -> Unit) {
        downstream.onTagContentUnsafe(block)
    }
}

fun region(): TagConsumer<RegionInfo> = WicketTagConsumer(
    downstream = HTMLStreamBuilder(
        out = StringBuilder(),
        prettyPrint = false
    ).onFinalizeMap { sb, _ -> sb.toString() })

