package org.kwicket.agilecoders.wicket.core.ajax.markup.html.bootstrap.navbar

import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.Navbar
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.NavbarAjaxLink
import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.behavior.Behavior
import org.apache.wicket.model.IModel
import org.kwicket.component.initComponent
import org.kwicket.model.model

open class KNavbarAjaxLink<T>(id: String = Navbar.componentId(),
                              model: IModel<T>? = null,
                              label: IModel<String?> = "".model(),
                              icon: IconType? = null,
                              val onClick: (AjaxRequestTarget, KNavbarAjaxLink<T>) -> Unit,
                              outputMarkupId: Boolean? = null,
                              outputMarkupPlaceholderTag: Boolean? = null,
                              visible: Boolean? = null,
                              enabled: Boolean? = null,
                              escapeModelStrings: Boolean? = null,
                              vararg behaviors: Behavior)
    : NavbarAjaxLink<T>(id, label) {

    init {
        initComponent(outputMarkupId = outputMarkupId,
                outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
                visible = visible,
                enabled = enabled,
                escapeModelStrings = escapeModelStrings,
                behaviors = *behaviors)
        model?.let { this.model = it }
        icon?.let { setIconType(it) }
    }

    override fun onClick(target: AjaxRequestTarget) {
        onClick.invoke(target, this)
    }
}
