package org.kwicket.agilecoders.wicket.core.ajax.markup.html

import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapAjaxLink
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons
import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType
import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.behavior.Behavior
import org.apache.wicket.model.IModel
import org.kwicket.component.init

// FIXME: change the icon to a model...
open class KBootstrapAjaxLink<T>(id: String,
                            model: IModel<T>? = null,
                            type: Buttons.Type = Buttons.Type.Default,
                            size: Buttons.Size? = null,
                            label: IModel<String>? = null,
                            val onClick: (AjaxRequestTarget, KBootstrapAjaxLink<T>) -> Unit, // FIXME: reverse the two params
                            icon: IconType? = null,
                            outputMarkupId: Boolean? = null,
                            outputMarkupPlaceholderTag: Boolean? = null,
                            vararg behaviors: Behavior)
    : BootstrapAjaxLink<T>(id, model, type, label) {

    init {
        init(
                outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
                outputMarkupId = outputMarkupId,
                behaviors = *behaviors)
        setIconType(icon)
        size?.let { setSize(it) }
    }

    override fun onClick(target: AjaxRequestTarget) {
        onClick.invoke(target, this)
    }
}