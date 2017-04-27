package org.kwicket.sample

import org.apache.wicket.Component
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn
import org.apache.wicket.markup.repeater.Item
import org.apache.wicket.model.IModel

class ButtonColumn<T, S>(label: IModel<String>, val buttonGenerator: (String) -> List<Component>) : AbstractColumn<T, S>(label) {
    override fun populateItem(item: Item<ICellPopulator<T>>, id: String, rowModel: IModel<T>) {
        item.add(ButtonPanel(id = id, model = rowModel, factory = buttonGenerator))
    }
}
