package org.kwicket.wicket.extensions.markup.html.tabs

import org.apache.wicket.extensions.markup.html.tabs.AbstractTab
import org.apache.wicket.markup.html.WebMarkupContainer
import org.apache.wicket.model.IModel
import org.kwicket.model.model
import org.kwicket.model.value

/**
 * Extends [AbstractTab] to allow the [panel] and visibility of the tab to be provided by a lambda and an [IModel],
 * respectively.
 *
 * @param model model of the title of the tab
 * @property visibleModel model of whether the tab is visible
 * @property panel lambda for producing the contents of the tab
 */
open class KAbstractTab(
    model: IModel<String>,
    private val visibleModel: IModel<Boolean> = true.model(),
    private val panel: (String) -> WebMarkupContainer
) : AbstractTab(model) {
    override fun getPanel(id: String): WebMarkupContainer = panel.invoke(id)
    override fun isVisible(): Boolean = visibleModel.value
}
