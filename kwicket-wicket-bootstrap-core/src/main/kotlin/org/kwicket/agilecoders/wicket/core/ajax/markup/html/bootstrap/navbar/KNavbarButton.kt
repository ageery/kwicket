package org.kwicket.agilecoders.wicket.core.ajax.markup.html.bootstrap.navbar

import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.NavbarButton
import org.apache.wicket.Page
import org.apache.wicket.model.IModel
import org.apache.wicket.request.mapper.parameter.PageParameters
import org.kwicket.model.model

open class KNavbarButton<T : Page>(
    pageClass: Class<T>,
    label: IModel<String>? = "".model(),
    params: PageParameters? = null,
    icon: IconType? = null
) : NavbarButton<T>(pageClass, params, label) {

    init {
        icon?.let { setIconType(icon) }
    }

}