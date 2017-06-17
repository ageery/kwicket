package org.kwicket.agilecoders.wicket.core.ajax.markup.html.bootstrap.navbar

import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.NavbarDropDownButton
import org.apache.wicket.markup.html.link.AbstractLink
import org.apache.wicket.model.IModel

open class KNavbarDropDownButton(model: IModel<String>,
                                 icon: IModel<IconType>? = null,
                                 val submenus: (String) -> List<AbstractLink>)
    : NavbarDropDownButton(model, icon) {

    override fun newSubMenuButtons(buttonMarkupId: String): MutableList<AbstractLink> = submenus.invoke(buttonMarkupId).toMutableList()

}
