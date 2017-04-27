package org.kwicket.agilecoders.wicket.core.ajax.markup.html

import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapAjaxLink
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons
import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType
import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.behavior.Behavior
import org.apache.wicket.model.IModel
import org.kwicket.AjaxClickHandler
import org.kwicket.component.initComponent
import org.kwicket.model.value

class KBootstrapAjaxLink<T>(id: String,
                            model: IModel<T>? = null,
                            type: Buttons.Type = Buttons.Type.Default,
                            label: IModel<String>? = null,
                            val onClick: AjaxClickHandler<T>,
                            icon: IconType? = null,
                            outputMarkupId: Boolean? = null,
                            outputMarkupPlaceholderTag: Boolean? = null,
                            vararg behaviors: Behavior)
    : BootstrapAjaxLink<T>(id, model, type, label) {

    init {
        initComponent(
                outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
                outputMarkupId = outputMarkupId,
                behaviors = *behaviors)
        setIconType(icon)
    }

    override fun onClick(target: AjaxRequestTarget) {
        onClick.invoke(target, model?.value)
    }
}