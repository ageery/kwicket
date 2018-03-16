package org.kwicket.kotlinx.html

import kotlinx.html.HTML
import kotlinx.html.HTMLTag
import kotlinx.html.HtmlBlockInlineTag
import kotlinx.html.HtmlBlockTag
import kotlinx.html.Tag
import kotlinx.html.TagConsumer
import kotlinx.html.visit
import kotlinx.html.visitAndFinalize
import org.apache.wicket.Component
import org.kwicket.wicketNamespacePrefix

fun <T, C : TagConsumer<T>> C.panel(block: PANEL.() -> Unit = {}): T = PANEL(
    this
).visitAndFinalize(this, block)
fun <T, C : TagConsumer<T>> C.border(block: BORDER.() -> Unit = {}): T = BORDER(
    this
).visitAndFinalize(this, block)
fun <T, C : TagConsumer<T>> C.extend(block: EXTEND.() -> Unit = {}): T = EXTEND(
    this
).visitAndFinalize(this, block)

fun HTML.panel(block: PANEL.() -> Unit = {}): Unit = PANEL(consumer).visit(block)
fun HTML.border(block: BORDER.() -> Unit = {}): Unit = BORDER(consumer).visit(block)
fun HTML.extend(block: EXTEND.() -> Unit = {}): Unit = EXTEND(consumer).visit(block)

val Tag.child: Unit
    get() {
        val tag = CHILD(consumer)
        consumer.onTagStart(tag)
        consumer.onTagEnd(tag)
    }

val Tag.body: Unit
    get() {
        val tag = BODY(consumer)
        consumer.onTagStart(tag)
        consumer.onTagEnd(tag)
    }

open class BODY(consumer: TagConsumer<*>) : HTMLTag(
    tagName = "${wicketNamespacePrefix}:body",
    consumer = consumer,
    initialAttributes = emptyMap(),
    inlineTag = true,
    emptyTag = true
),
    HtmlBlockInlineTag

open class CHILD(consumer: TagConsumer<*>) : HTMLTag(
    tagName = "${wicketNamespacePrefix}:child",
    consumer = consumer,
    initialAttributes = emptyMap(),
    inlineTag = true,
    emptyTag = true
),
    HtmlBlockInlineTag

abstract class PanelTag(tagName: String, consumer: TagConsumer<*>) : HTMLTag(
    tagName = tagName,
    consumer = consumer,
    initialAttributes = emptyMap(),
    inlineTag = false,
    emptyTag = false
), HtmlBlockTag

class PANEL(consumer: TagConsumer<*>) : PanelTag(
    tagName = "${wicketNamespacePrefix}:panel",
    consumer = consumer
), HtmlBlockTag

class BORDER(consumer: TagConsumer<*>) : HTMLTag(
    tagName = "${wicketNamespacePrefix}:border",
    consumer = consumer,
    initialAttributes = emptyMap(),
    inlineTag = false,
    emptyTag = false
), HtmlBlockTag

class EXTEND(consumer: TagConsumer<*>) : PanelTag(
    tagName = "${wicketNamespacePrefix}:extend",
    consumer = consumer
), HtmlBlockTag

/*
 * A Wicket Tag is defined to be a builder with either an explicit or implicit id.
 * That is, the processor of a WicketTag may need to provide/generate an id for use with the builder.
 */
interface WicketTag {
    val id: String?
    val builder: ((String) -> Component)
}
