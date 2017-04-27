package org.kwicket.sample

import org.apache.wicket.Component
import org.apache.wicket.markup.html.panel.Panel
import org.apache.wicket.model.IModel
import org.apache.wicket.model.util.ListModel
import org.kwicket.wicket.core.markup.html.form.KForm
import org.kwicket.wicket.core.markup.html.list.KListView
import org.kwicket.component.q
import org.kwicket.model.value

class ButtonPanel(id: String, model: IModel<*>, factory: (String) -> List<Component>) : Panel(id, model) {

    init {
        q(KForm(id = "form", model = model))
        q(KListView(id = "buttons",
                model = ListModel(factory.invoke("button")),
                populate = { item -> item.add(item.model.value) }))
    }

}
