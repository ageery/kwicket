package org.kwicket.kotlinx.html.queued

import org.apache.wicket.MarkupContainer
import org.apache.wicket.behavior.Behavior
import org.apache.wicket.model.IModel
import org.kwicket.component.q
import org.kwicket.kotlinx.html.PANEL
import org.kwicket.kotlinx.html.RegionInfoPanel
import org.kwicket.kotlinx.html.panel
import org.kwicket.kotlinx.html.region


fun <T> MarkupContainer.inlinePanel(
    id: String,
    model: IModel<T>,
    visible: Boolean? = null,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    renderBodyOnly: Boolean? = null,
    behaviors: List<Behavior>? = null,
    panel: PANEL.(IModel<T>) -> Unit
) = q(
    RegionInfoPanel(
        id = id,
        model = model,
        visible = visible,
        outputMarkupId = outputMarkupId,
        outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
        renderBodyOnly = renderBodyOnly,
        behaviors = behaviors,
        region = { region().panel { panel(model) } }
    )
)