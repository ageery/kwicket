package org.kwicket.agilecoders.wicket.core.ajax.markup.html.bootstrap.navbar

import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.Navbar
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.NavbarText
import org.apache.wicket.behavior.Behavior
import org.apache.wicket.model.IModel
import org.kwicket.component.init

open class KNavbarText(
    id: String = Navbar.componentId(),
    model: IModel<String?>,
    position: Navbar.ComponentPosition? = null,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    vararg behaviors: Behavior
) : NavbarText(id, model) {

    init {
        init(
            outputMarkupId = outputMarkupId,
            outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
            behaviors = *behaviors
        )
        position?.let { this.position(it) }
    }

}
