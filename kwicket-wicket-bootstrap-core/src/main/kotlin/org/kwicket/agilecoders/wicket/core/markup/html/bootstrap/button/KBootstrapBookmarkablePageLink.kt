package org.kwicket.agilecoders.wicket.core.markup.html.bootstrap.button

import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapBookmarkablePageLink
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons
import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType
import org.apache.wicket.Page
import org.apache.wicket.behavior.Behavior
import org.apache.wicket.model.IModel
import org.apache.wicket.request.mapper.parameter.PageParameters
import org.kwicket.component.init
import kotlin.reflect.KClass

open class KBootstrapBookmarkablePageLink<C: Page>(
    id: String,
    label: IModel<*>? = null,
    page: KClass<C>,
    params: PageParameters? = null,
    type: Buttons.Type? = null,
    size: Buttons.Size? = null,
    iconType: IconType? = null,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    escapeModelStrings: Boolean? = null,
    renderBodyOnly: Boolean? = null,
    behaviors: List<Behavior>? = null
) : BootstrapBookmarkablePageLink<Unit>(id, page.java, params, type) {
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
        label?.let { setLabel(it) }
        size?.let { setSize(it) }
        iconType?.let { setIconType(it) }
    }
}