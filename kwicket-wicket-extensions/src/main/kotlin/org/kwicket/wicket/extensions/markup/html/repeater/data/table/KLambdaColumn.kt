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