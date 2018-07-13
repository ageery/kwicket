package org.kwicket.wicket.core.markup.html.link

import org.apache.wicket.markup.html.link.PopupSettings

class KPopupSettings(
    target: String? = null,
    height: Int? = null,
    left: Int? = null,
    width: Int? = null,
    windowName: String? = null,
    displayFlags: Int? = null
) : PopupSettings(windowName, displayFlags?.let { it } ?: 0) {

    init {
        target?.let { setTarget(it) }
        height?.let { setHeight(it) }
        left?.let { setLeft(it) }
        width?.let { setWidth(it) }
    }

}