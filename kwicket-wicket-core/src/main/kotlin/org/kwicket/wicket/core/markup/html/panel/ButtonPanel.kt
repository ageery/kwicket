package org.kwicket.wicket.core.markup.html.panel

import org.apache.wicket.behavior.Behavior
import org.apache.wicket.markup.html.form.Button
import org.apache.wicket.markup.html.form.Form
import org.kwicket.component.init
import org.kwicket.component.q
import org.kwicket.model.listModel
import org.kwicket.model.value
import org.kwicket.wicket.core.queued.listView

/**
 * I think this should be depreciated
 */
open class ButtonPanel(
    id: String,
    buttons: (String, Form<*>?) -> List<Button>,
    form: Form<*>? = null,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    renderBodyOnly: Boolean? = null,
    escapeModelStrings: Boolean? = null,
    behaviors: List<Behavior>? = null
) : KPanel(id = id) {

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
        listView(id = "buttons", model = buttons("button", form).listModel()) {
            q(model.value)
        }
    }

}