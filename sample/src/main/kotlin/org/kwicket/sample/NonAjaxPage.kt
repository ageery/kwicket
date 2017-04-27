package org.kwicket.sample

import org.apache.wicket.event.Broadcast
import org.kwicket.behavior.VisibleWhen
import org.kwicket.component.onEvent
import org.kwicket.component.q
import org.kwicket.model.ldm
import org.kwicket.model.model
import org.kwicket.model.value
import org.kwicket.wicket.core.markup.html.basic.KLabel
import org.kwicket.wicket.core.markup.html.form.KButton
import org.kwicket.wicket.core.markup.html.form.KCheckbox
import org.kwicket.wicket.core.markup.html.form.KForm
import org.kwicket.wicket.core.markup.html.form.KTextField
import org.kwicket.wicket.core.markup.html.link.KLink
import org.kwicket.wicket.core.markup.html.panel.KFeedbackPanel
import org.wicketstuff.annotation.mount.MountPath

class NonAjaxResetEvent

@MountPath("non-ajax")
class NonAjaxPage : BasePage() {

    init {
        val defaultName = "test"
        val defaultShowLabel = true
        val showLabelModel = defaultShowLabel.model()
        val nameModel = defaultName.model<String?>()

        q(KForm(id = "form", model = nameModel))
        q(KFeedbackPanel(id = "feedback"))
        q(KLabel(id = "nameLabel", model = { nameModel.value ?: "<None>" }.ldm(), behaviors = VisibleWhen({ showLabelModel.value })))
        q(KLabel(id = "linkLabel", model = { if (showLabelModel.value) "Hide" else "Show" }.ldm()))
        q(KCheckbox(id = "check", model = showLabelModel))
        q(KLink<Any>(id = "link", onClick = { showLabelModel.value = !showLabelModel.value }))
        q(KTextField(id = "name", model = nameModel, label = "New Name".model(), required = true)
                .onEvent({ c, _: NonAjaxResetEvent -> c.clearInput() }))
        q(KButton(id = "updateButton", onSubmit = {}, onError = {}))
        q(KButton(id = "resetButton", defaultFormProcessing = false,
                onSubmit = {
                    nameModel.value = defaultName
                    showLabelModel.value = defaultShowLabel
                    send(page, Broadcast.BREADTH, NonAjaxResetEvent())
                }
        ))
        q(KButton(id = "clearButton",
                defaultFormProcessing = false,
                onSubmit = {
                    nameModel.value = null
                    send(page, Broadcast.BREADTH, NonAjaxResetEvent())
                }))
    }

}