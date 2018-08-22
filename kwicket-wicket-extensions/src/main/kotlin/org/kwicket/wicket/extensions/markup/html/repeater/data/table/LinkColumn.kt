package org.kwicket.wicket.extensions.markup.html.repeater.data.table

import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator
import org.apache.wicket.markup.html.link.AbstractLink
import org.apache.wicket.markup.repeater.Item
import org.apache.wicket.model.IModel
import org.kwicket.wicket.core.markup.html.panel.LinkPanel

/**
 * Table column where the column cell is a list of links.
 *
 * @param T type of data in a row of the table
 * @param S type of the sort used in the table
 * @param displayModel model of the table column header label
 * @param sortProperty optional sort used for the column
 * @param cssClasses list of CSS classes to add to the column
 * @param links lambda to create a list of links to display in the table cell
 */
open class LinkColumn<T, S>(
    displayModel: IModel<String>,
    sortProperty: S? = null,
    cssClasses: List<String>? = null,
    private val links: (String, IModel<T>) -> List<AbstractLink>
) : KAbstractColumn<T, S>(displayModel = displayModel, sortProperty = sortProperty, cssClasses = cssClasses) {

    override fun populateItem(item: Item<ICellPopulator<T>>, id: String, rowModel: IModel<T>) {
        item.add(LinkPanel(id = id, model = rowModel, links = links))
    }

}