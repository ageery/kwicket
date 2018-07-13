package org.kwicket.wicket.core.markup.html.link

import org.apache.wicket.behavior.Behavior
import org.apache.wicket.markup.html.link.ExternalLink
import org.apache.wicket.markup.html.link.PopupSettings
import org.apache.wicket.model.IModel
import org.kwicket.component.init

/**
 * [ExternalLink] with named and default constructor arguments.
 */
// TODO: add popup options
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

