package org.kwicket.wicket.extensions.markup.html.repeater.data.table

import org.apache.wicket.Component
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator
import org.apache.wicket.extensions.markup.html.repeater.data.table.LambdaColumn
import org.apache.wicket.markup.repeater.Item
import org.apache.wicket.model.IModel
import org.danekja.java.util.function.serializable.SerializableFunction

private class KSerializableFunction<T, R>(val function: (T) -> R) : SerializableFunction<T, R> {
    override fun apply(t: T): R = function(t)
}

/**
 * [LambdaColumn] with named parameters.
 *
 * @param T type of data in a row of the data table
 * @param S type of sort used in the data table
 * @param displayModel model of the column header label
 * @param sort sort object used for the column
 * @param function lambda for creating a value for the column cell from the row value
 * @param header lambda for creating the column header component
 * @param cssClasses list of CSS classes to add to the column
 * @param populateItem lambda for populating the column cell
 */
open class KLambdaColumn<T, S>(
    displayModel: IModel<String>,
    sort: S? = null,
    function: (T) -> Any? = {},
    private val header: ((String, IModel<String>) -> Component)? = null,
    private val populateItem: ((Item<ICellPopulator<T>>, String, IModel<T>, IModel<*>) -> Unit)? = null,
    private val cssClasses: List<String>? = null
) : LambdaColumn<T, S>(
    displayModel, sort,
    KSerializableFunction(function = function)
) {

    override fun getHeader(componentId: String): Component =
        header?.invoke(componentId, displayModel) ?: super.getHeader(componentId)

    override fun populateItem(item: Item<ICellPopulator<T>>, componentId: String, rowModel: IModel<T>) =
        populateItem?.invoke(item, componentId, rowModel, getDataModel(rowModel))
                ?: super.populateItem(item, componentId, rowModel)

    override fun getCssClass(): String? = cssClasses?.joinToString(separator = " ") ?: super.getCssClass()
}