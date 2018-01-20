package org.kwicket.agilecoders.wicket.core.ajax.markup.html.bootstrap.button

import de.agilecoders.wicket.core.markup.html.bootstrap.button.BootstrapAjaxButton
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons
import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType
import org.apache.wicket.WicketRuntimeException
import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.behavior.Behavior
import org.apache.wicket.markup.html.form.Form
import org.apache.wicket.model.IModel
import org.kwicket.component.init

// FIXME: change the icon to an IModel...
open class KBootstrapAjaxButton(id: String,
                                model: IModel<String>? = null,
                                val onSubmit: (AjaxRequestTarget, KBootstrapAjaxButton) -> Unit,
                                defaultFormProcessing: Boolean? = null,
                                form: Form<*>? = null,
                                val onError: ((AjaxRequestTarget, KBootstrapAjaxButton) -> Unit)? = null,
                                outputMarkupId: Boolean? = null,
                                outputMarkupPlaceholderTag: Boolean? = null,
                                type: Buttons.Type = Buttons.Type.Default,
                                icon: IconType? = null,
                                size: Buttons.Size? = null,
                                vararg behaviors: Behavior)
    : BootstrapAjaxButton(id, model, form, type) {

    init {
        init(outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
                outputMarkupId = outputMarkupId,
                behaviors = *behaviors)
        defaultFormProcessing?.let { this.defaultFormProcessing = it }
        icon?.let { setIconType(it) }
        size?.let { setSize(it) }
    }

    override fun onSubmit(target: AjaxRequestTarget) {
        onSubmit.invoke(target, this)
    }

    override fun onError(target: AjaxRequestTarget) {
        onError?.invoke(target, this) ?: throw WicketRuntimeException("No onError handler defined for ${javaClass.name} with id=$id")
    }

}