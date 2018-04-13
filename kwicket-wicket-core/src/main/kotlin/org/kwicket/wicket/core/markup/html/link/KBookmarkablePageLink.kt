package org.kwicket.wicket.core.markup.html.link

import org.apache.wicket.Page
import org.apache.wicket.behavior.Behavior
import org.apache.wicket.markup.html.link.BookmarkablePageLink
import org.apache.wicket.request.mapper.parameter.PageParameters
import org.kwicket.component.init
import kotlin.reflect.KClass

/**
 * [BookmarkablePageLink] with named and default constructor arguments.
 */
open class KBookmarkablePageLink<C : Page>(
    id: String,
    page: KClass<C>,
    params: PageParameters? = null,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    behaviors: List<Behavior>? = null
) : BookmarkablePageLink<C>(id, page.java, params) {

    init {
        init(
            outputMarkupId = outputMarkupId,
            outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
            visible = visible,
            enabled = enabled,
            behaviors = behaviors
        )
    }

}

