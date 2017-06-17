package org.kwicket.wicketstuff.select2

import org.wicketstuff.select2.AbstractSelect2Choice
import org.wicketstuff.select2.ISelect2Theme

fun AbstractSelect2Choice<*, *>.initSelect2(width: String? = null,
                                      placeholder: String? = null,
                                      closeOnSelect: Boolean? = null,
                                      allowClear: Boolean? = null,
                                      theme: ISelect2Theme? = null) {
    width?.let { settings.width = it }
    placeholder?.let { settings.placeholder = it }
    closeOnSelect?.let { settings.closeOnSelect = it }
    allowClear?.let { settings.allowClear = it }
    theme?.let { settings.theme = it }
}

