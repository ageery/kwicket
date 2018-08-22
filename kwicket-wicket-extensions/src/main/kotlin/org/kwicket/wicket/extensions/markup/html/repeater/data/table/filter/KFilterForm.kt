package org.kwicket.wicket.extensions.markup.html.repeater.data.table.filter

import org.apache.wicket.behavior.Behavior
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.FilterForm
import org.apache.wicket.extensions.markup.html.repeater.data.table.filter.IFilterStateLocator
import org.kwicket.component.init

/**
 * [FilterForm] with named parameters.
 *
 * @param T type of the model backing the component
 * @param id Wicket component id
 * @param locator filter state locator
 * @param outputMarkupId whether to output a markup id for the [FilterForm]
 * @param outputMarkupPlaceholderTag whether to output a placeholder tag even if the component is initially not visible
 * @param visible whether the component is visible
 * @param enabled whether the component is enabled
 * @param renderBodyOnly whether to only render the component itself and not the tag the component is embedded in
 * @param escapeModelStrings whether to escape model strings
 * @param behaviors [Behavior]s to add to the component
 */
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