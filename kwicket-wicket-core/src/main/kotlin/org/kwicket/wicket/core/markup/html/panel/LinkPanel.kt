package org.kwicket.wicket.core.markup.html.panel

import org.apache.wicket.behavior.Behavior
import org.apache.wicket.markup.html.link.AbstractLink
import org.apache.wicket.model.IModel
import org.kwicket.component.init
import org.kwicket.model.listModel
import org.kwicket.model.value
import org.kwicket.wicket.core.markup.html.list.KListView

class LinkPanel<T>(id: String,
                   model: IModel<T>,
                   links: (String, IModel<T>) -> List<AbstractLink>,
                   outputMarkupId: Boolean? = null,
                   outputMarkupPlaceholderTag: Boolean? = null,
                   visible: Boolean? = null,
                   enabled: Boolean? = null,
                   renderBodyOnly: Boolean? = null,
                   escapeModelStrings: Boolean? = null,
                   vararg behaviors: Behavior)
    : KPanel(id = id, model = model) {

    init {
        init(outputMarkupId = outputMarkupId,
                outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
                visible = visible,
                enabled = enabled,
                escapeModelStrings = escapeModelStrings,
                renderBodyOnly = renderBodyOnly,
                behaviors = *behaviors)
        add(KListView(id = "links",
                model = links.invoke("link", model).listModel(),
                populate = { item -> item.add(item.model.value) }))
    }

}