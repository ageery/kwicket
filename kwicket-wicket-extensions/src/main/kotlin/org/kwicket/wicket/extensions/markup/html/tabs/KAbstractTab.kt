package org.kwicket.wicket.extensions.markup.html.tabs

import org.apache.wicket.extensions.markup.html.tabs.AbstractTab
import org.apache.wicket.markup.html.WebMarkupContainer
import org.apache.wicket.model.IModel
import org.kwicket.model.model
import org.kwicket.model.value

open class KAbstractTab(model: IModel<String>,
                        private val visibleModel: IModel<Boolean> = true.model(),
                        private val panel: (String) -> WebMarkupContainer) : AbstractTab(model) {

    override fun getPanel(id: String): WebMarkupContainer {
        return panel.invoke(id)
    }

    override fun isVisible(): Boolean = visibleModel.value

}
