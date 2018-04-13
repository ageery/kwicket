package org.kwicket.agilecoders.wicket.core.ajax.markup.html.bootstrap.navbar

import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.INavbarComponent
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.Navbar
import org.apache.wicket.behavior.Behavior
import org.apache.wicket.model.IModel
import org.kwicket.component.init

open class KNavbar(
    id: String,
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
    fluid: Boolean? = null,
    components: ((String) -> List<List<INavbarComponent>>)? = null,
    behaviors: List<Behavior>? = null
) : Navbar(id, model) {

    init {
        init(
            outputMarkupId = outputMarkupId,
            outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
            visible = visible,
            enabled = enabled,
            escapeModelStrings = escapeModelStrings,
            renderBodyOnly = renderBodyOnly,
            behaviors = behaviors
        )
        position?.let { this.position = it }
        inverted?.let { this.setInverted(it) }
        brandName?.let { this.setBrandName(brandName) }
        fluid?.let { if (it) this.fluid() }
        components?.let { it.invoke(Navbar.componentId()).forEach { addComponents(it) } }
    }

}