package org.kwicket.component

import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn
import org.apache.wicket.extensions.markup.html.repeater.data.table.export.IExportableColumn
import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.markup.repeater.Item
import org.apache.wicket.model.IModel
import org.kwicket.model.KPropModel
import org.kwicket.model.PropChain

class KPropertyColumn<T, S, D>(label: IModel<String>, val props: PropChain<D>, sort: S? = null) : AbstractColumn<T, S>(label, sort), IExportableColumn<T, S> {

    override fun populateItem(item: Item<ICellPopulator<T>>, id: String, rowModel: IModel<T>) {
        item.add(Label(id, getDataModel(rowModel)))
    }

    override fun getDataModel(rowModel: IModel<T>): IModel<*> = KPropModel(rowModel, props)
}