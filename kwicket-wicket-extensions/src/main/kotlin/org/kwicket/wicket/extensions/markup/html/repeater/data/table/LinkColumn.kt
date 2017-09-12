package org.kwicket.wicket.extensions.markup.html.repeater.data.table

import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator
import org.apache.wicket.markup.html.link.AbstractLink
import org.apache.wicket.markup.repeater.Item
import org.apache.wicket.model.IModel
import org.kwicket.wicket.core.markup.html.panel.LinkPanel

class LinkColumn<T, S>(displayModel: IModel<String>,
                       private val links: (String, IModel<T>) -> List<AbstractLink>,
                       sortProperty: S? = null,
                       vararg cssClasses: String)
    : KAbstractColumn<T, S>(displayModel = displayModel, sortProperty = sortProperty, cssClasses = *cssClasses) {

    override fun populateItem(item: Item<ICellPopulator<T>>, id: String, rowModel: IModel<T>) {
        item.add(LinkPanel(id = id, model = rowModel, links = links))
    }

}