package org.kwicket.agilecoders.wicket.core.ajax.markup.html.bootstrap.navbar

import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.Navbar
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.NavbarComponents
import org.apache.wicket.Component

fun positionLeft(vararg components: Component) =
        NavbarComponents.transform(Navbar.ComponentPosition.LEFT, *components)
fun positionRight(vararg components: Component) =
        NavbarComponents.transform(Navbar.ComponentPosition.RIGHT, *components)
