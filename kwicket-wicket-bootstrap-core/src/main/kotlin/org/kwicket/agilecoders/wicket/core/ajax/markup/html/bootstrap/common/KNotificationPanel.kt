package org.kwicket.agilecoders.wicket.core.ajax.markup.html.bootstrap.common

import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationPanel
import org.apache.wicket.Component
import org.apache.wicket.behavior.Behavior
import org.apache.wicket.feedback.IFeedbackMessageFilter
import org.apache.wicket.util.time.Duration
import org.kwicket.component.init

class KNotificationPanel(
    id: String,
    fence: Component? = null,
    filter: IFeedbackMessageFilter? = null,
    hideAfter: Duration? = null,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    escapeModelStrings: Boolean? = null,
    behaviors: List<Behavior>? = null
) : NotificationPanel(id, fence, filter) {

    init {
        init(
            outputMarkupId = outputMarkupId,
            outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
            visible = visible,
            enabled = enabled,
            escapeModelStrings = escapeModelStrings,
            behaviors = behaviors
        )
        hideAfter?.let { this.hideAfter(it) }
    }

}