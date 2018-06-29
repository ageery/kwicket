package org.kwicket.agilecoders.wicket.core.ajax.markup.html.bootstrap.tabs

import de.agilecoders.wicket.core.markup.html.bootstrap.tabs.ClientSideBootstrapTabbedPanel
import org.apache.wicket.behavior.Behavior
import org.apache.wicket.extensions.markup.html.tabs.ITab
import org.apache.wicket.model.IModel
import org.kwicket.component.init

class KClientSideBootstrapTabbedPanel<T : ITab>(
    id: String,
    tabs: List<T>,
    model: IModel<Int>? = null,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    escapeModelStrings: Boolean? = null,
    renderBodyOnly: Boolean? = null,
    behaviors: List<Behavior>? = null
) : ClientSideBootstrapTabbedPanel<T>(id, tabs, model) {

    init {
        init(
            outputMarkupId = outputMarkupId,
            outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
            visible = visible,
            enabled = enabled,
            escapeModelStrings = escapeModelStrings,
            renderBodyOnly = renderBodyOnly,
            behaviors = behaviors
        )
    }

}