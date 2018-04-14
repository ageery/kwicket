package org.kwicket.agilecoders.wicket.extensions.markup.html.bootstrap.table.toolbars

import de.agilecoders.wicket.core.markup.html.bootstrap.image.GlyphIconType
import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType
import de.agilecoders.wicket.extensions.markup.html.bootstrap.table.sort.BootstrapOrderByBorder
import de.agilecoders.wicket.extensions.markup.html.bootstrap.table.toolbars.BootstrapHeadersToolbar
import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.sort.AjaxOrderByLink
import org.apache.wicket.extensions.markup.html.repeater.data.sort.ISortStateLocator
import org.apache.wicket.extensions.markup.html.repeater.data.sort.OrderByLink
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable
import org.apache.wicket.markup.html.WebMarkupContainer
import org.kwicket.component.refresh

open class BootstrapAjaxFallbackHeadersToolbar<S>(table: DataTable<*, S>, stateLocator: ISortStateLocator<S>) : BootstrapHeadersToolbar<S>(table, stateLocator) {

    override fun newSortableHeader(headerId: String, property: S, locator: ISortStateLocator<S>): WebMarkupContainer {
        return object : BootstrapOrderByBorder<S>(headerId, property, locator) {

            override fun ascendingIconType(): IconType {
                return GlyphIconType.sortbyattributes
            }

            override fun descendingIconType(): IconType {
                return GlyphIconType.sortbyattributesalt
            }

            override fun unsortedIconType(): IconType {
                return GlyphIconType.sort
            }

            override fun newOrderByLink(id: String, property: S, stateLocator: ISortStateLocator<S>?): OrderByLink<S> =
                    object : AjaxOrderByLink<S>(id, property, stateLocator) {

                        override fun onClick(target: AjaxRequestTarget) {
                            table.refresh(target = target)
                        }

                        override fun onSortChanged() {
                            super.onSortChanged()
                            table.currentPage = 0
                        }

                    }
        }
    }

}