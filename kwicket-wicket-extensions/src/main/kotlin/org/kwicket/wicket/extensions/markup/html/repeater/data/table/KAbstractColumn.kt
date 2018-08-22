package org.kwicket.wicket.extensions.markup.html.repeater.data.table

import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn
import org.apache.wicket.model.IModel

/**
 * [AbstractColumn] with named parameters.
 *
 * @param T type of a row in the data table
 * @param S type of the sort in the data table
 * @param displayModel model of the column header label
 * @param sortProperty sort object for the column
 * @property cssClasses optional list of CSS classes to add to the column
 */
abstract class KAbstractColumn<T, S>(
    displayModel: IModel<String>,
    sortProperty: S? = null,
    private val cssClasses: List<String>? = null
) : AbstractColumn<T, S>(displayModel, sortProperty) {

    override fun getCssClass(): String? = cssClasses?.joinToString(separator = " ")

}
