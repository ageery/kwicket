package org.kwicket.wicket.extensions.markup.html.repeater.data.table.filter

import org.apache.wicket.behavior.Behavior
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilterStateLocator
import org.kwicket.component.init

open class KFilterForm<T>(
    id: String,
    locator: IFilterStateLocator<T>,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    renderBodyOnly: Boolean? = null,
    escapeModelStrings: Boolean? = null,
    behaviors: List<Behavior>? = null
) : FilterForm<T>(id, locator) {

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