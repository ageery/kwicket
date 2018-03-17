package org.kwicket.agilecoders.wicket.extensions.markup.html.bootstrap.form.select

import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.select.BootstrapSelectConfig

data class MaxOptionsText(
    val maxOptionsText: String,
    val groupText: String,
    val singleItem: String,
    val multiItem: String
)

open class KBootstrapSelectConfig(
    liveSearch: Boolean? = null,
    multiple: Boolean? = null,
    maxOptions: Int? = null,
    maxOptionsText: MaxOptionsText? = null,
    noResultText: String? = null,
    selectedTitle: String? = null,
    noneSelectedText: String? = null,
    countSelectedText: String? = null,
    actionsBox: Boolean? = null,
    selectAllText: String? = null,
    deselectAllText: String? = null
) : BootstrapSelectConfig() {

    init {
        liveSearch?.let { withLiveSearch(it) }
        multiple?.let { withMultiple(it) }
        maxOptions?.let { withMaxOptions(it) }
        maxOptionsText?.let { withMaxOptionsText(it.maxOptionsText, it.groupText, it.singleItem, it.multiItem) }
        noResultText?.let { withNoResultText(it) }
        selectedTitle?.let { withSelectedTitle(it) }
        noneSelectedText?.let { withNoneSelectedText(it) }
        countSelectedText?.let { withCountSelectedText(it) }
        actionsBox?.let { withActionsBox(it) }
        selectAllText?.let { withSelectAllText(it) }
        deselectAllText?.let { withDeselectAllText(it) }
    }

}