package org.kwicket.wicket.core.markup.html.list

import org.apache.wicket.behavior.Behavior
import org.apache.wicket.markup.html.list.ListItem
import org.apache.wicket.markup.html.list.ListView
import org.apache.wicket.model.IModel
import org.kwicket.component.init

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
 * @param behaviors zero or more [Behavior]s to be added to the [Component]
 */
open class KListView<T, C : List<T>>(id: String,
                                     model: IModel<C>? = null,
                                     val populate: (ListItem<T>) -> Unit,
                                     reuseItems: Boolean? = null,
                                     outputMarkupId: Boolean? = null,
                                     outputMarkupPlaceholderTag: Boolean? = null,
                                     visible: Boolean? = null,
                                     enabled: Boolean? = null,
                                     renderBodyOnly: Boolean? = null,
                                     escapeModelStrings: Boolean? = null,
                                     vararg behaviors: Behavior)
    : ListView<T>(id, model) {

    init {
        init(outputMarkupId = outputMarkupId,
                outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
                visible = visible,
                enabled = enabled,
                escapeModelStrings = escapeModelStrings,
                renderBodyOnly = renderBodyOnly,
                behaviors = *behaviors)
        reuseItems?.let { this.reuseItems = it }
    }

    override fun populateItem(item: ListItem<T>) = populate(item)

}