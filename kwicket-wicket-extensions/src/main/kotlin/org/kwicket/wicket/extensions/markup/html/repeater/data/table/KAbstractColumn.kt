package org.kwicket.wicket.extensions.markup.html.repeater.data.table

import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn
import org.apache.wicket.model.IModel

abstract class KAbstractColumn<T, S>(displayModel: IModel<String>, sortProperty: S? = null, val cssClasses: String? = null) : AbstractColumn<T, S>(displayModel, sortProperty) {

    override fun getCssClass(): String? = cssClasses

}