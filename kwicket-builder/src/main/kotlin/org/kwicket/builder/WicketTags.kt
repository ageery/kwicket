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
import kotlinx.html.stream.appendHTML
import kotlinx.html.visit
import kotlinx.html.visitAndFinalize
import org.apache.wicket.Component
import org.apache.wicket.util.resource.IResourceStream
import org.kwicket.toResourceStream

/*
 * TODO: other Wicket attributes and tags: https://cwiki.apache.org/confluence/display/WICKET/Wicket%27s+XHTML+tags
 */

private const val ns = "wicket"
private const val idAttr = "$ns:id"

fun <T, C : TagConsumer<T>> C.panel(block: PANEL.() -> Unit = {}): T = PANEL(this).visitAndFinalize(this, block)
fun <T, C : TagConsumer<T>> C.border(block: BORDER.() -> Unit = {}): T = BORDER(this).visitAndFinalize(this, block)
fun <T, C : TagConsumer<T>> C.extend(block: EXTEND.() -> Unit = {}): T = EXTEND(this).visitAndFinalize(this, block)

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
    tagName = "$ns:body",
    consumer = consumer,
    initialAttributes = emptyMap(),
    inlineTag = true,
    emptyTag = true
),
    HtmlBlockInlineTag

open class CHILD(consumer: TagConsumer<*>) : HTMLTag(
    tagName = "$ns:child",
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
    tagName = "$ns:panel",
    consumer = consumer
), HtmlBlockTag

class BORDER(consumer: TagConsumer<*>) : HTMLTag(
    tagName = "$ns:border",
    consumer = consumer,
    initialAttributes = emptyMap(),
    inlineTag = false,
    emptyTag = false
), HtmlBlockTag

class EXTEND(consumer: TagConsumer<*>) : PanelTag(
    tagName = "$ns:extend",
    consumer = consumer
), HtmlBlockTag

fun FlowContent.form(id: String? = null, classes: String? = null, block: FORM.() -> Unit = {}): Unit =
    FORM(attributesMapOf("class", classes, idAttr, id), consumer).visit(block)

/*
 * For each tag, there are two special Wicket variations:
 *  - Wicket id but no builder -- extends the regular <TAG> class
 *  - Wicket builder with an optional Wicket id -- extends the WICKET_<TAG> class
 */

fun FlowOrPhrasingContent.span(
    id: String,
    classes: String? = null,
    block: SPAN.() -> Unit = {}
): Unit =
    SPAN(attributesMapOf("class", classes, idAttr, id), consumer).visit(block)

fun FlowOrPhrasingContent.span(
    id: String? = null,
    builder: ((String) -> Component),
    classes: String? = null,
    block: WICKET_SPAN.() -> Unit = {}
): Unit =
    WICKET_SPAN(id, builder, attributesMapOf("class", classes, idAttr, id), consumer).visit(block)

fun FlowContent.div(
    id: String? = null,
    builder: ((String) -> Component),
    classes: String? = null,
    block: WICKET_DIV.() -> Unit = {}
): Unit =
    WICKET_DIV(id, builder, attributesMapOf("class", classes, idAttr, id), consumer).visit(block)

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

fun panel(block: PANEL.() -> Unit = {}): IResourceStream =
    buildString {
        appendHTML().panel(block = block)
    }.toResourceStream()

fun border(block: BORDER.() -> Unit = {}): IResourceStream =
    buildString {
        appendHTML().border(block = block)
    }.toResourceStream()

fun extend(block: EXTEND.() -> Unit = {}): IResourceStream =
    buildString {
        appendHTML().extend(block = block)
    }.toResourceStream()

/*
 * A Wicket Tag is defined to be a builder with either an explicit or implicit id.
 * That is the processor of a WicketTag may need to provide/generate an id for use with the builder.
 */
interface WicketTag {
    val id: String?
    val builder: ((String) -> Component)
}

fun attrs(initialAttributes: Map<String, String>, id: String?) =
        if (id == null) initialAttributes else initialAttributes + ("wicket:id" to id)

class WICKET_SPAN(
    override val id: String? = null,
    override val builder: (String) -> Component,
    initialAttributes: Map<String, String>,
    consumer: TagConsumer<*>
) : SPAN(initialAttributes = attrs(initialAttributes, id), consumer = consumer),
    WicketTag

class WICKET_DIV(
    override val id: String? = null,
    override val builder: (String) -> Component,
    initialAttributes: Map<String, String>,
    consumer: TagConsumer<*>
) : DIV(initialAttributes = attrs(initialAttributes, id), consumer = consumer),
    WicketTag
