package org.kwicket.wicket.core.markup.html.link

import org.apache.wicket.MarkupContainer
import org.apache.wicket.behavior.Behavior
import org.apache.wicket.markup.html.link.ExternalLink
import org.apache.wicket.markup.html.link.PopupSettings
import org.apache.wicket.model.IModel
import org.kwicket.component.init
import org.kwicket.component.q

fun externalLink(
    model: IModel<String>,
    label: IModel<*>? = null,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    behaviors: List<Behavior>? = null,
    popupSettings: PopupSettings? = null
): (String) -> ExternalLink = {
    KExternalLink(
        id = it,
        model = model,
        label = label,
        outputMarkupId = outputMarkupId,
        outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
        visible = visible,
        enabled = enabled,
        behaviors = behaviors,
        popupSettings = popupSettings
    )
}

fun MarkupContainer.externalLink(
    id: String,
    model: IModel<String>,
    label: IModel<*>? = null,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    behaviors: List<Behavior>? = null,
    popupSettings: PopupSettings? = null
) = q(
    KExternalLink(
        id = id,
        model = model,
        label = label,
        outputMarkupId = outputMarkupId,
        outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
        visible = visible,
        enabled = enabled,
        behaviors = behaviors,
        popupSettings = popupSettings
    )
)

/**
 * [ExternalLink] with named and default constructor arguments.
 */
open class KExternalLink(
    id: String,
    model: IModel<String>,
    label: IModel<*>? = null,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    behaviors: List<Behavior>? = null,
    popupSettings: PopupSettings? = null
) : ExternalLink(id, model, label) {

    init {
        init(
            outputMarkupId = outputMarkupId,
            outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
            visible = visible,
            enabled = enabled,
            behaviors = behaviors
        )
        popupSettings?.let { setPopupSettings(it) }
    }

}

