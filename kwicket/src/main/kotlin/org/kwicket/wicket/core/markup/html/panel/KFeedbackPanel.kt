package org.kwicket.wicket.core.markup.html.panel

import org.apache.wicket.feedback.IFeedbackMessageFilter
import org.apache.wicket.markup.html.panel.FeedbackPanel
import org.kwicket.component.initComponent

class KFeedbackPanel(id: String, filter: IFeedbackMessageFilter? = null, outputMarkupPlaceholderTag: Boolean? = null,
                     outputMarkupId: Boolean? = null) : FeedbackPanel(id, filter) {

    init {
        initComponent(outputMarkupPlaceholderTag = outputMarkupPlaceholderTag, outputMarkupId = outputMarkupId)
    }

}