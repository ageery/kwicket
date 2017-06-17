package org.kwicket.wicket.core.markup.html.link

import org.apache.wicket.Page
import org.apache.wicket.behavior.Behavior
import org.apache.wicket.markup.html.link.BookmarkablePageLink
import org.apache.wicket.request.mapper.parameter.PageParameters
import org.kwicket.component.initComponent

open class KBookmarkablePageLink<C: Page>(id: String,
                                          page: Class<C>,
                                          params: PageParameters? = null,
                                          outputMarkupId: Boolean? = null,
                                          outputMarkupPlaceholderTag: Boolean? = null,
                                          visible: Boolean? = null,
                                          enabled: Boolean? = null,
                                          vararg behaviors: Behavior)
    : BookmarkablePageLink<C>(id, page, params) {

    init {
        initComponent(outputMarkupId = outputMarkupId,
                outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
                visible = visible,
                enabled = enabled,
                behaviors = *behaviors)
    }

}

