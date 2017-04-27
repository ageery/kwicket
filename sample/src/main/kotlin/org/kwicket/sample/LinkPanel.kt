package org.kwicket.sample

import org.apache.wicket.markup.html.link.AbstractLink
import org.apache.wicket.markup.html.panel.Panel
import org.apache.wicket.model.IModel
import org.apache.wicket.model.util.ListModel
import org.kwicket.model.value
import org.kwicket.wicket.core.markup.html.list.KListView

class LinkPanel<T>(id: String,
                   model: IModel<T>,
                   generator: (String, IModel<T>) -> List<AbstractLink>)
    : Panel(id, model) {

    init {

//        val links = KLink<String>(id = "links", onClick = {})
//        add(links)
//        val label = KLabel(id = "link", model = "test".model())
//        links.add(label)
//
//        q(object : ListView<AbstractLink>("links", ListModel(generator.invoke("link", model))) {
//            override fun populateItem(item: ListItem<AbstractLink>) {
//                //item.add(item.model.value)
//                item.add(Label("link", "Test".model()))
//            }
//        })
        add(KListView(id = "links",
                model = ListModel(generator.invoke("link", model)),
                populate = { item -> item.add(item.model.value) }))
    }

}
