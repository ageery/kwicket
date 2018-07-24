package org.kwicket.wicket.core.markup.html.list

import org.apache.wicket.MarkupContainer
import org.apache.wicket.behavior.Behavior
import org.apache.wicket.markup.html.list.ListItem
import org.apache.wicket.markup.html.list.ListView
import org.apache.wicket.model.IModel
import org.kwicket.component.init
import org.kwicket.component.q

@Deprecated(message = "Use the method in teh queued package", replaceWith = ReplaceWith(expression = "org.kwicket.wicket.core.queued.listView"))
fun <T, C : List<T>> MarkupContainer.listView(
    id: String,
    model: IModel<C>? = null,
    reuseItems: Boolean? = null,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    renderBodyOnly: Boolean? = null,
    escapeModelStrings: Boolean? = null,
    behaviors: List<Behavior>? = null,
    populate: ListItem<T>.() -> Unit
) = q(
    KListView(
        id = id,
        model = model,
        reuseItems = reuseItems,
        outputMarkupId = outputMarkupId,
        outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
        visible = visible,
        enabled = enabled,
        renderBodyOnly = renderBodyOnly,
        escapeModelStrings = escapeModelStrings,
        behaviors = behaviors,
        populate = { populate(it) }
    )
)

//fun <T, C : List<T>> MarkupContainer.listView(
//    id: String,
//    model: IModel<C>? = null,
//    reuseItems: Boolean? = null,
//    outputMarkupId: Boolean? = null,
//    outputMarkupPlaceholderTag: Boolean? = null,
//    visible: Boolean? = null,
//    enabled: Boolean? = null,
//    renderBodyOnly: Boolean? = null,
//    escapeModelStrings: Boolean? = null,
//    behaviors: List<Behavior>? = null,
//    populate: (ListItem<T>) -> Unit
//) = q(
//    KListView(
//        id = id,
//        model = model,
//        reuseItems = reuseItems,
//        outputMarkupId = outputMarkupId,
//        outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
//        visible = visible,
//        enabled = enabled,
//        renderBodyOnly = renderBodyOnly,
//        escapeModelStrings = escapeModelStrings,
//        behaviors = behaviors,
//        populate = populate
//    )
//)

/**
 * [ListView] with named and default constructor arguments.
 *
 * @param id id of the [Component]
 * @param model optional [IModel] of the [Component]
 * @param outputMarkupId optional flag indicating whether an id attribute will be created on the HTML element
 * @param outputMarkupPlaceholderTag optional flag indicating whether an id attribtue will be created on the HTML
 *      element, creating a placeholder id if the component is initially not visible
 * @param visible optional flag indicating whether the [Component] is visible
 * @param enabled optional flag indicating whether the [Component] is enabled
 * @param renderBodyOnly optional flag indicating whether only the [Component]'s HTML will be rendered or whether the
 *      tag the [Component] is attached to will also be rendered
 * @param escapeModelStrings optional flag indicating whether the [Component]'s model String values will be escaped
 * @param behaviors [List] of [Behavior]s to add to the [Component]
 */
open class KListView<T, C : List<T>>(
    id: String,
    model: IModel<C>? = null,
    reuseItems: Boolean? = null,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    renderBodyOnly: Boolean? = null,
    escapeModelStrings: Boolean? = null,
    behaviors: List<Behavior>? = null,
    val populate: (ListItem<T>) -> Unit
) : ListView<T>(id, model) {

    init {
        init(
            outputMarkupId = outputMarkupId,
            outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
            visible = visible,
            enabled = enabled,
            escapeModelStrings = escapeModelStrings,
            renderBodyOnly = renderBodyOnly,
            behaviors = behaviors
        )
        reuseItems?.let { this.reuseItems = it }
    }

    override fun populateItem(item: ListItem<T>) = populate(item)

}