package org.kwicket.agilecoders.wicket.core.ajax.markup.html.bootstrap.navbar

import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.Navbar
import org.apache.wicket.behavior.Behavior
import org.apache.wicket.model.IModel
import org.kwicket.component.init

class KNavbar(id: String,
              model: IModel<*>? = null,
              position: Navbar.Position? = null,
              inverted: Boolean? = null,
              brandName: IModel<String>? = null,
              outputMarkupId: Boolean? = null,
              outputMarkupPlaceholderTag: Boolean? = null,
              visible: Boolean? = null,
              enabled: Boolean? = null,
              escapeModelStrings: Boolean? = null,
              renderBodyOnly: Boolean? = null,
              vararg behaviors: Behavior)
    : Navbar(id, model) {

    init {
        init(outputMarkupId = outputMarkupId,
                outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
                visible = visible,
                enabled = enabled,
                escapeModelStrings = escapeModelStrings,
                renderBodyOnly = renderBodyOnly,
                behaviors = *behaviors)
        position?.let { this.position = it }
        inverted?.let { this.setInverted(it) }
        brandName?.let { this.setBrandName(brandName) }
    }

}