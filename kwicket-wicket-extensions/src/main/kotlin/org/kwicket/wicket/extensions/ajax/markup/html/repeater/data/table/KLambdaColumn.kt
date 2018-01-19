package org.kwicket.wicket.extensions.ajax.markup.html.repeater.data.table

import org.apache.wicket.extensions.markup.html.repeater.data.table.LambdaColumn
import org.apache.wicket.model.IModel
import org.danekja.java.util.function.serializable.SerializableFunction

private class KSerializableFunction<T, R>(val function: (T) -> R) : SerializableFunction<T, R> {
    override fun apply(t: T): R = function(t)
}


class KLambdaColumn<T, S>(displayModel: IModel<String>,
                          sort: S?,
                          function: (T) -> Any?)
    : LambdaColumn<T, S>(displayModel, sort, KSerializableFunction(function = function))