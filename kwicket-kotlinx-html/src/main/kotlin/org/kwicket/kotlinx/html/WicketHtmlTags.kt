package org.kwicket.kotlinx.html

import kotlinx.html.A
import kotlinx.html.BUTTON
import kotlinx.html.DIV
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
import kotlinx.html.TagConsumer
import kotlinx.html.TextAreaWrap
import kotlinx.html.ThScope
import kotlinx.html.UL
import kotlinx.html.attributes.enumEncode
import kotlinx.html.attributesMapOf
import kotlinx.html.stream.appendHTML
import kotlinx.html.visit
import org.apache.wicket.Component
import org.apache.wicket.behavior.AttributeAppender
import org.apache.wicket.model.IModel
import org.apache.wicket.util.resource.IResourceStream
import org.kwicket.behavior.VisibleWhen
import org.kwicket.component.init
import org.kwicket.toResourceStream
import org.kwicket.wicket.core.markup.html.KWebMarkupContainer
import org.kwicket.wicket.core.markup.html.basic.KLabel
import org.kwicket.wicketIdAttr

fun FlowOrPhrasingContent.span(
    id: String,
    classes: String? = null,
    block: SPAN.() -> Unit = {}
): Unit =
    SPAN(attributesMapOf("class", classes, wicketIdAttr, id), consumer).visit(block)

fun FlowOrPhrasingContent.span(
    model: IModel<*>,
    classes: String? = null,
    block: WICKET_SPAN.() -> Unit = {}
): Unit =
    WICKET_SPAN(
        id = null,
        builder = { KLabel(it, model) },
        initialAttributes = attributesMapOf("class", classes),
        consumer = consumer
    ).visit(block)

fun FlowOrPhrasingContent.span(
    id: String? = null,
    builder: ((String) -> Component),
    classes: String? = null,
    block: WICKET_SPAN.() -> Unit = {}
): Unit =
    WICKET_SPAN(
        id,
        builder,
        attributesMapOf("class", classes, wicketIdAttr, id),
        consumer
    ).visit(block)

fun FlowOrPhrasingContent.span(
    id: String? = null,
    classes: List<String>? = null,
    visible: (() -> Boolean)? = null,
    model: IModel<*>,
    block: WICKET_SPAN.() -> Unit = {}
): Unit =
    WICKET_SPAN(
        id,
        {
            KLabel(id = it, model = model, behaviors = listOfNotNull(
                visible?.let { VisibleWhen { visible() } },
                classes?.let { AttributeAppender("class", classes.joinToString(" "), " ") }
            ))
        },
        attributesMapOf(wicketIdAttr, id),
        consumer
    ).visit(block)

// FIXME: add a function to generate a CSS class behavior -- "row".toCssClass() = Behavior; "row".css() = Behavior

fun FlowContent.div(
    id: String? = null,
    classes: String? = null,
    builder: ((String) -> Component) = { KWebMarkupContainer(id = it) },
    block: WICKET_DIV.() -> Unit = {}
): Unit =
    WICKET_DIV(
        id,
        builder,
        attributesMapOf("class", classes, wicketIdAttr, id),
        consumer
    ).visit(block)

fun FlowContent.div(
    id: String? = null,
    classes: List<String>? = null,
    visible: (() -> Boolean)? = null,
    block: WICKET_DIV.() -> Unit = {}
): Unit =
    WICKET_DIV(
        id,
        {
            KWebMarkupContainer(id = it, behaviors = listOfNotNull(
                visible?.let { VisibleWhen { visible() } },
                classes?.let { AttributeAppender("class", classes.joinToString(" "), " ") }
            ))
        },
        attributesMapOf(wicketIdAttr, id),
        consumer
    ).visit(block)

fun FlowContent.p(id: String? = null, classes: String? = null, block: P.() -> Unit = {}): Unit =
    P(attributesMapOf("class", classes, wicketIdAttr, id), consumer).visit(block)

fun FlowOrInteractiveOrPhrasingContent.a(
    id: String? = null,
    classes: String? = null,
    block: A.() -> Unit = {}
): Unit = A(attributesMapOf(wicketIdAttr, id, "class", classes), consumer).visit(block)

fun FlowContent.ul(id: String? = null, classes: String? = null, block: UL.() -> Unit = {}): Unit =
    UL(attributesMapOf("class", classes, wicketIdAttr, id), consumer).visit(block)

fun FlowOrPhrasingContent.a(
    classes: List<String>? = null,
    builder: (String) -> Component,
    block: WICKET_A.() -> Unit = {}
): Unit =
    WICKET_A(
        id = null,
        builder = { builder(it).init(behaviors = listOfNotNull(
            classes?.let { AttributeAppender("class", classes.joinToString(" "), " ") }
        ))},
        consumer = consumer
    ).visit(block)

fun FlowOrPhrasingContent.ul(
    classes: List<String>? = null,
    block: WICKET_UL.() -> Unit = {}
): Unit =
    WICKET_UL(
        id = null,
        builder = { KWebMarkupContainer(id = it, behaviors = listOfNotNull(
            classes?.let { AttributeAppender("class", classes.joinToString(" "), " ") }
        ))},
        consumer = consumer
    ).visit(block)

fun FlowOrPhrasingContent.li(
    builder: (String) -> Component,
    block: WICKET_LI.() -> Unit = {}
): Unit =
    WICKET_LI(
        id = null,
        builder = builder,
        consumer = consumer
    ).visit(block)

fun FlowOrPhrasingContent.li(
    classes: List<String>? = null,
    model: IModel<*>,
    block: WICKET_LI.() -> Unit = {}
): Unit =
    WICKET_LI(
        id = null,
        builder = { KLabel(id = it, model = model, behaviors = listOfNotNull(
            classes?.let { AttributeAppender("class", classes.joinToString(" "), " ") }
        ))},
        consumer = consumer
    ).visit(block)

fun FlowContent.ol(id: String? = null, classes: String? = null, block: OL.() -> Unit = {}): Unit =
    OL(attributesMapOf("class", classes, wicketIdAttr, id), consumer).visit(block)

fun UL.li(id: String? = null, classes: String? = null, block: LI.() -> Unit = {}): Unit =
    LI(attributesMapOf("class", classes, wicketIdAttr, id), consumer).visit(block)

fun FlowContent.table(id: String? = null, classes: String? = null, block: TABLE.() -> Unit = {}): Unit =
    TABLE(attributesMapOf("class", classes, wicketIdAttr, id), consumer).visit(block)

fun FlowOrHeadingContent.h1(id: String? = null, classes: String? = null, block: H1.() -> Unit = {}): Unit =
    H1(attributesMapOf("class", classes, wicketIdAttr, id), consumer).visit(block)

fun FlowOrHeadingContent.h2(id: String? = null, classes: String? = null, block: H2.() -> Unit = {}): Unit =
    H2(attributesMapOf("class", classes, wicketIdAttr, id), consumer).visit(block)

fun FlowOrHeadingContent.h3(id: String? = null, classes: String? = null, block: H3.() -> Unit = {}): Unit =
    H3(attributesMapOf("class", classes, wicketIdAttr, id), consumer).visit(block)

fun FlowOrHeadingContent.h4(id: String? = null, classes: String? = null, block: H4.() -> Unit = {}): Unit =
    H4(attributesMapOf("class", classes, wicketIdAttr, id), consumer).visit(block)

fun FlowOrHeadingContent.h5(
    id: String? = null,
    classes: String? = null,
    block: H5.() -> Unit = {}
): Unit = H5(attributesMapOf("class", classes, wicketIdAttr, id), consumer).visit(block)

fun FlowOrHeadingContent.h6(id: String? = null, classes: String? = null, block: H6.() -> Unit = {}): Unit =
    H6(attributesMapOf("class", classes, wicketIdAttr, id), consumer).visit(block)

fun FlowOrInteractiveOrPhrasingContent.button(
    id: String? = null,
    classes: String? = null,
    block: BUTTON.() -> Unit = {}
): Unit = BUTTON(attributesMapOf("class", classes, wicketIdAttr, id), consumer).visit(block)

fun FlowOrInteractiveOrPhrasingContent.input(
    id: String? = null,
    type: InputType? = null,
    classes: String? = null,
    block: INPUT.() -> Unit = {}
): Unit = INPUT(attributesMapOf("type", type?.enumEncode(), "class", classes, wicketIdAttr, id), consumer).visit(block)

fun FlowOrInteractiveOrPhrasingContent.img(
    id: String? = null,
    classes: String? = null,
    block: IMG.() -> Unit = {}
): Unit = IMG(
    attributesMapOf("class", classes, wicketIdAttr, id),
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
    attributesMapOf(
        "rows", rows, "cols", cols, "wrap", wrap?.enumEncode(), "class", classes,
        wicketIdAttr, id
    ),
    consumer
).visit(block)

fun FlowOrInteractiveOrPhrasingContent.label(
    id: String? = null,
    classes: String? = null,
    block: LABEL.() -> Unit = {}
): Unit = LABEL(attributesMapOf("class", classes, wicketIdAttr, id), consumer).visit(block)

fun FlowOrPhrasingContent.label(
    id: String? = null,
    builder: ((String) -> Component),
    classes: String? = null,
    block: WICKET_LABEL.() -> Unit = {}
): Unit =
    WICKET_LABEL(
        id,
        builder,
        attributesMapOf("class", classes, wicketIdAttr, id),
        consumer
    ).visit(block)

fun FlowOrPhrasingContent.label(
    id: String? = null,
    classes: List<String>? = null,
    visible: (() -> Boolean)? = null,
    model: IModel<*>,
    block: WICKET_LABEL.() -> Unit = {}
): Unit =
    WICKET_LABEL(
        id,
        {
            KLabel(id = it, model = model, behaviors = listOfNotNull(
                visible?.let { VisibleWhen { visible() } },
                classes?.let { AttributeAppender("class", classes.joinToString(" "), " ") }
            ))
        },
        attributesMapOf(wicketIdAttr, id),
        consumer
    ).visit(block)

fun FlowOrInteractiveOrPhrasingContent.select(
    id: String? = null,
    classes: String? = null,
    block: SELECT.() -> Unit = {}
): Unit = SELECT(attributesMapOf("class", classes, wicketIdAttr, id), consumer).visit(block)

fun TABLE.tr(id: String? = null, classes: String? = null, block: TR.() -> Unit = {}): Unit =
    TR(attributesMapOf("class", classes, wicketIdAttr, id), consumer).visit(block)

fun TR.td(id: String? = null, classes: String? = null, block: TD.() -> Unit = {}): Unit =
    TD(attributesMapOf("class", classes, wicketIdAttr, id), consumer).visit(block)

fun TR.th(id: String? = null, scope: ThScope? = null, classes: String? = null, block: TH.() -> Unit = {}): Unit =
    TH(attributesMapOf("scope", scope?.enumEncode(), "class", classes, wicketIdAttr, id), consumer).visit(block)

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

fun attrs(initialAttributes: Map<String, String>, id: String?) =
    if (id == null) initialAttributes else initialAttributes + (wicketIdAttr to id)

class WICKET_UL(
    override val id: String? = null,
    override val builder: (String) -> Component,
    initialAttributes: Map<String, String> = emptyMap(),
    consumer: TagConsumer<*>
) : UL(initialAttributes = attrs(initialAttributes, id), consumer = consumer),
    WicketTag

class WICKET_LI(
    override val id: String? = null,
    override val builder: (String) -> Component,
    initialAttributes: Map<String, String> = emptyMap(),
    consumer: TagConsumer<*>
) : LI(initialAttributes = attrs(initialAttributes, id), consumer = consumer),
    WicketTag

class WICKET_A(
    override val id: String? = null,
    override val builder: (String) -> Component,
    initialAttributes: Map<String, String> = emptyMap(),
    consumer: TagConsumer<*>
) : A(initialAttributes = attrs(initialAttributes, id), consumer = consumer),
    WicketTag

class WICKET_SPAN(
    override val id: String? = null,
    override val builder: (String) -> Component,
    initialAttributes: Map<String, String>,
    consumer: TagConsumer<*>
) : SPAN(initialAttributes = attrs(initialAttributes, id), consumer = consumer),
    WicketTag

class WICKET_LABEL(
    override val id: String? = null,
    override val builder: (String) -> Component,
    initialAttributes: Map<String, String>,
    consumer: TagConsumer<*>
) : LABEL(initialAttributes = attrs(initialAttributes, id), consumer = consumer),
    WicketTag

class WICKET_DIV(
    override val id: String? = null,
    override val builder: (String) -> Component,
    initialAttributes: Map<String, String>,
    consumer: TagConsumer<*>
) : DIV(initialAttributes = attrs(initialAttributes, id), consumer = consumer),
    WicketTag