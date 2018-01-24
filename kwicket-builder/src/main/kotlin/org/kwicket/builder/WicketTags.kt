package org.kwicket.builder

import kotlinx.html.A
import kotlinx.html.BUTTON
import kotlinx.html.DIV
import kotlinx.html.FORM
import kotlinx.html.FlowContent
import kotlinx.html.FlowOrHeadingContent
import kotlinx.html.FlowOrInteractiveOrPhrasingContent
import kotlinx.html.FlowOrPhrasingContent
import kotlinx.html.H1
import kotlinx.html.H2
import kotlinx.html.H3
import kotlinx.html.H4
import kotlinx.html.H5
import kotlinx.html.H6
import kotlinx.html.HTML
import kotlinx.html.HTMLTag
import kotlinx.html.HtmlBlockInlineTag
import kotlinx.html.HtmlBlockTag
import kotlinx.html.IMG
import kotlinx.html.INPUT
import kotlinx.html.InputType
import kotlinx.html.LABEL
import kotlinx.html.LI
import kotlinx.html.OL
import kotlinx.html.P
import kotlinx.html.SELECT
import kotlinx.html.SPAN
import kotlinx.html.TABLE
import kotlinx.html.TD
import kotlinx.html.TEXTAREA
import kotlinx.html.TH
import kotlinx.html.TR
import kotlinx.html.Tag
import kotlinx.html.TagConsumer
import kotlinx.html.TextAreaWrap
import kotlinx.html.ThScope
import kotlinx.html.UL
import kotlinx.html.attributes.enumEncode
import kotlinx.html.attributesMapOf
import kotlinx.html.visit
import kotlinx.html.visitAndFinalize

/*
 * TODO: other Wicket attributes and tags: https://cwiki.apache.org/confluence/display/WICKET/Wicket%27s+XHTML+tags
 */

private const val ns = "wicket"
private const val idAttr = "$ns:id"

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

open class BODY(consumer : TagConsumer<*>) : HTMLTag(
    tagName = "$ns:body",
    consumer = consumer,
    initialAttributes = emptyMap(),
    inlineTag = true,
    emptyTag = true),
    HtmlBlockInlineTag

open class CHILD(consumer : TagConsumer<*>) : HTMLTag(
    tagName = "$ns:child",
    consumer = consumer,
    initialAttributes = emptyMap(),
    inlineTag = true,
    emptyTag = true),
    HtmlBlockInlineTag

class PANEL(consumer: TagConsumer<*>) : HTMLTag(
    tagName = "$ns:panel",
    consumer = consumer,
    initialAttributes = emptyMap(),
    inlineTag = false,
    emptyTag = false
), HtmlBlockTag

class BORDER(consumer: TagConsumer<*>) : HTMLTag(
    tagName = "$ns:border",
    consumer = consumer,
    initialAttributes = emptyMap(),
    inlineTag = false,
    emptyTag = false
), HtmlBlockTag

class EXTEND(consumer: TagConsumer<*>) : HTMLTag(
    tagName = "$ns:extend",
    consumer = consumer,
    initialAttributes = emptyMap(),
    inlineTag = false,
    emptyTag = false
), HtmlBlockTag

fun <T, C : TagConsumer<T>> C.panel(block: PANEL.() -> Unit = {}): T = PANEL(this).visitAndFinalize(this, block)

fun FlowContent.form(id: String? = null, classes: String? = null, block: FORM.() -> Unit = {}): Unit =
    FORM(attributesMapOf("class", classes, idAttr, id), consumer).visit(block)

fun FlowOrPhrasingContent.span(id: String? = null, classes: String? = null, block: SPAN.() -> Unit = {}): Unit =
    SPAN(attributesMapOf("class", classes, idAttr, id), consumer).visit(block)

fun FlowContent.div(id: String? = null, classes: String? = null, block: DIV.() -> Unit = {}): Unit =
    DIV(attributesMapOf("class", classes, idAttr, id), consumer).visit(block)

fun FlowContent.p(id: String? = null, classes: String? = null, block: P.() -> Unit = {}): Unit =
    P(attributesMapOf("class", classes, idAttr, id), consumer).visit(block)

fun FlowOrInteractiveOrPhrasingContent.a(
    id: String? = null,
    classes: String? = null,
    block: A.() -> Unit = {}
): Unit = A(attributesMapOf(idAttr, id, "class", classes), consumer).visit(block)

fun FlowContent.ul(id: String? = null, classes: String? = null, block: UL.() -> Unit = {}): Unit =
    UL(attributesMapOf("class", classes, idAttr, id), consumer).visit(block)

fun FlowContent.ol(id: String? = null, classes: String? = null, block: OL.() -> Unit = {}): Unit =
    OL(attributesMapOf("class", classes, idAttr, id), consumer).visit(block)

fun UL.li(id: String? = null, classes: String? = null, block: LI.() -> Unit = {}): Unit =
    LI(attributesMapOf("class", classes, idAttr, id), consumer).visit(block)

fun FlowContent.table(id: String? = null, classes: String? = null, block: TABLE.() -> Unit = {}): Unit =
    TABLE(attributesMapOf("class", classes, idAttr, id), consumer).visit(block)

fun FlowOrHeadingContent.h1(id: String? = null, classes: String? = null, block: H1.() -> Unit = {}): Unit =
    H1(attributesMapOf("class", classes, idAttr, id), consumer).visit(block)

fun FlowOrHeadingContent.h2(id: String? = null, classes: String? = null, block: H2.() -> Unit = {}): Unit =
    H2(attributesMapOf("class", classes, idAttr, id), consumer).visit(block)

fun FlowOrHeadingContent.h3(id: String? = null, classes: String? = null, block: H3.() -> Unit = {}): Unit =
    H3(attributesMapOf("class", classes, idAttr, id), consumer).visit(block)

fun FlowOrHeadingContent.h4(id: String? = null, classes: String? = null, block: H4.() -> Unit = {}): Unit =
    H4(attributesMapOf("class", classes, idAttr, id), consumer).visit(block)

fun FlowOrHeadingContent.h5(
    id: String? = null,
    classes: String? = null,
    block: H5.() -> Unit = {}
): Unit = H5(attributesMapOf("class", classes, idAttr, id), consumer).visit(block)

fun FlowOrHeadingContent.h6(id: String? = null, classes: String? = null, block: H6.() -> Unit = {}): Unit =
    H6(attributesMapOf("class", classes, idAttr, id), consumer).visit(block)

fun FlowOrInteractiveOrPhrasingContent.button(
    id: String? = null,
    classes: String? = null,
    block: BUTTON.() -> Unit = {}
): Unit = BUTTON(attributesMapOf("class", classes, idAttr, id), consumer).visit(block)

fun FlowOrInteractiveOrPhrasingContent.input(
    id: String? = null,
    type: InputType? = null,
    classes: String? = null,
    block: INPUT.() -> Unit = {}
): Unit = INPUT(attributesMapOf("type", type?.enumEncode(), "class", classes, idAttr, id), consumer).visit(block)

fun FlowOrInteractiveOrPhrasingContent.img(
    id: String? = null,
    classes: String? = null,
    block: IMG.() -> Unit = {}
): Unit = IMG(
    attributesMapOf("class", classes, idAttr, id),
    consumer
).visit(block)

fun FlowOrInteractiveOrPhrasingContent.textArea(
    id: String? = null,
    rows: String? = null,
    cols: String? = null,
    wrap: TextAreaWrap? = null,
    classes: String? = null,
    block: TEXTAREA.() -> Unit = {}
): Unit = TEXTAREA(
    attributesMapOf("rows", rows, "cols", cols, "wrap", wrap?.enumEncode(), "class", classes, idAttr, id),
    consumer
).visit(block)

fun FlowOrInteractiveOrPhrasingContent.label(
    id: String? = null,
    classes: String? = null,
    block: LABEL.() -> Unit = {}
): Unit = LABEL(attributesMapOf("class", classes, idAttr, id), consumer).visit(block)

fun FlowOrInteractiveOrPhrasingContent.select(
    id: String? = null,
    classes: String? = null,
    block: SELECT.() -> Unit = {}
): Unit = SELECT(attributesMapOf("class", classes, idAttr, id), consumer).visit(block)

fun TABLE.tr(id: String? = null, classes: String? = null, block: TR.() -> Unit = {}): Unit =
    TR(attributesMapOf("class", classes, idAttr, id), consumer).visit(block)

fun TR.td(id: String? = null, classes: String? = null, block: TD.() -> Unit = {}): Unit =
    TD(attributesMapOf("class", classes, idAttr, id), consumer).visit(block)

fun TR.th(id: String? = null, scope: ThScope? = null, classes: String? = null, block: TH.() -> Unit = {}): Unit =
    TH(attributesMapOf("scope", scope?.enumEncode(), "class", classes, idAttr, id), consumer).visit(block)