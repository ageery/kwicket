package org.kwicket.agilecoders.wicket.core.ajax.markup.html.bootstrap.navbar

import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.NavbarButton
import org.apache.wicket.Page
import org.apache.wicket.behavior.Behavior
import org.apache.wicket.model.IModel
import org.apache.wicket.request.mapper.parameter.PageParameters
import org.kwicket.component.init
import org.kwicket.model.model
import kotlin.reflect.KClass

open class KNavbarButton<T : Page>(
    pageClass: KClass<T>,
    label: IModel<String>? = "".model(),
    params: PageParameters? = null,
    icon: IconType? = null,
    outputMarkupId: Boolean? = null,
    behaviors: List<Behavior>? = null
) : NavbarButton<T>(pageClass.java, params, label) {

    init {
        init(outputMarkupId = outputMarkupId,
            behaviors = behaviors)
        icon?.let { setIconType(icon) }
    }

}