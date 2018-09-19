package org.kwicket.wicket.core.markup.html.form

import org.apache.wicket.WicketRuntimeException
import org.apache.wicket.behavior.Behavior
import org.apache.wicket.markup.html.form.Button
import org.apache.wicket.model.IModel
import org.kwicket.NonAjaxHandler
import org.kwicket.component.init

/**
 * [Button] with named and default constructor arguments.
 */
open class KButton(
    id: String,
    model: IModel<String>? = null,
    defaultFormProcessing: Boolean? = null,
    private val onSubmit: NonAjaxHandler,
    private val onError: NonAjaxHandler? = null,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    escapeModelStrings: Boolean? = null,
    enabled: Boolean? = null,
    visible: Boolean? = null,
    required: Boolean? = null,
    label: IModel<String>? = null,
    behaviors: List<Behavior>? = null
) : Button(id, model) {

    init {
        init(
            outputMarkupId = outputMarkupId,
            outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
            behaviors = behaviors,
            escapeModelStrings = escapeModelStrings,
            renderBodyOnly = renderBodyOnly,
            enabled = enabled,
            visible = visible,
            required = required,
            label = label
        )
        defaultFormProcessing?.let { this.defaultFormProcessing = it }
        //this.defaultFormProcessing = defaultFormProcessing ?: this.defaultFormProcessing
    }

    override fun onSubmit() {
        onSubmit.invoke()
    }

    override fun onError() {
        onError?.invoke()
                ?: throw WicketRuntimeException("No onError handler defined for ${javaClass.name} with id=$id")
    }

}