package org.kwicket.kotlinx.html

import org.apache.wicket.model.IModel
import org.kwicket.component.q
import org.kwicket.wicket.core.markup.html.list.KListView
import org.kwicket.wicket.core.markup.html.panel.KPanel

class ListViewRegion<T, C : List<T>>(
    id: String,
    model: IModel<C>,
    region: (IModel<T>) -> RegionInfo
) : KPanel(id = id, model = model) {

    init {
        q(
            KListView(
                id = "view",
                renderBodyOnly = true,
                model = model,
                populate = { listItem ->
                    listItem.add(RegionInfoPanel(id = "content", model = listItem.model, region = region, renderBodyOnly = true))
                }
            )
        )
    }

}