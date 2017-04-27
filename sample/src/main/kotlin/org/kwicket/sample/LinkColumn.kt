package org.kwicket.sample

import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn
import org.apache.wicket.markup.html.link.AbstractLink
import org.apache.wicket.markup.html.panel.Panel
import org.apache.wicket.markup.repeater.Item
import org.apache.wicket.model.IModel

class LinkColumn<T, S>(label: IModel<String>, val generator: (String, IModel<T>) -> List<AbstractLink>) : AbstractColumn<T, S>(label) {

    override fun populateItem(item: Item<ICellPopulator<T>>, id: String, rowModel: IModel<T>) {
        // FIXME: the problem seems to be here...
        ////item.add(LinkPanel(id = id, model = rowModel, generator = generator))
        //item.add(Label(id, "test".model()))
        item.add(LinkColumnPanel(id, rowModel, generator))
    }

    class LinkColumnPanel<T>(id: String, model: IModel<T>, generator: (String, IModel<T>) -> List<AbstractLink>) : Panel(id, model) {
        init {
            add(LinkPanel("panel", model, generator))
        }
    }

}
