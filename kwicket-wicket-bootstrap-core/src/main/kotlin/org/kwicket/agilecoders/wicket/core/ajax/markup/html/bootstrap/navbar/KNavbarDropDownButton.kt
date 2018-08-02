package org.kwicket.agilecoders.wicket.core.ajax.markup.html.bootstrap.navbar

import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.NavbarDropDownButton
import org.apache.wicket.behavior.Behavior
import org.apache.wicket.model.IModel
import org.kwicket.component.init
import org.kwicket.model.model

abstract class KNavbarDropDownButton(
    model: IModel<String>,
    icon: IModel<IconType?> = null.model(),
    behaviors: List<Behavior>? = null
) : NavbarDropDownButton(model, icon) {

    constructor(
        model: IModel<String>,
        icon: IModel<IconType?> = null.model(),
        behavior: Behavior
    ) : this(model = model, icon = icon, behaviors = listOf(behavior))

    init {
        init(behaviors = behaviors)
    }

}
