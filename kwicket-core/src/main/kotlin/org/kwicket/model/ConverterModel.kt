package org.kwicket.model

import org.apache.wicket.model.IModel
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

open class ConverterModel<F, T>(
    val sourceModel: IModel<F>,
    val toTarget: (F) -> T,
    val fromTarget: (T) -> F
) : IModel<T> {

    override fun getObject(): T = toTarget(sourceModel.value)

    override fun setObject(targetValue: T) {
        sourceModel.value = fromTarget(targetValue)
    }

    override fun detach() {
        super.detach()
        sourceModel.detach()
    }

}

fun toDateModel(localDateModel: IModel<LocalDate?>) =
    ConverterModel<LocalDate?, Date?>(sourceModel = localDateModel,
        toTarget = { it?.let { java.sql.Date.valueOf(it) } },
        fromTarget = { it?.let { it.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() } })
