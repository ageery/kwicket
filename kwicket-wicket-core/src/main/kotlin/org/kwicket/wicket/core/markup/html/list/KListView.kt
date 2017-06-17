package org.kwicket.wicket.core.markup.html.list

import org.apache.wicket.behavior.Behavior
import org.apache.wicket.markup.html.list.ListItem
import org.apache.wicket.markup.html.list.ListView
import org.apache.wicket.model.IModel
import org.kwicket.component.initComponent

class KListView<T, C: List<T>>(id: String,
                   model: IModel<C>? = null,
                   val populate: (ListItem<T>) -> Unit,
                   reuseItems: Boolean? = null,
                   outputMarkupId: Boolean? = null,
                   outputMarkupPlaceholderTag: Boolean? = null,
                   vararg behaviors: Behavior)
    : ListView<T>(id, model) {

    init {
        initComponent(outputMarkupId = outputMarkupId,
                outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
                behaviors = *behaviors)
        reuseItems?.let { this.reuseItems = it }
    }

    override fun populateItem(item: ListItem<T>) = populate(item)

}