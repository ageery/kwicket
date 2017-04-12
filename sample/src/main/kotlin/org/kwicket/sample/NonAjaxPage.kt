package org.kwicket.sample

import org.apache.wicket.event.Broadcast
import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.markup.html.form.CheckBox
import org.apache.wicket.markup.html.form.Form
import org.apache.wicket.markup.html.form.TextField
import org.apache.wicket.markup.html.panel.FeedbackPanel
import org.kwicket.NonAjaxSubmitHandler
import org.kwicket.component.button
import org.kwicket.component.handleEvent
import org.kwicket.component.link
import org.kwicket.component.q
import org.kwicket.component.required
import org.kwicket.component.visibleWhen
import org.kwicket.model.model
import org.kwicket.model.value
import org.wicketstuff.annotation.mount.MountPath

@MountPath("non-ajax")
class NonAjaxPage : BasePage() {

    init {
        val defaultName = "test"
        val defaultShowLabel = true
        val showLabelModel = defaultShowLabel.model()
        val nameModel = defaultName.model<String?>()

        q(Form("form", nameModel))
        q(FeedbackPanel("feedback"))
        q(Label("nameLabel", { nameModel.value ?: "<None>" })
                .visibleWhen(showLabelModel))
        q(Label("linkLabel", { if (showLabelModel.value) "Hide" else "Show" }))
        q(CheckBox("check", showLabelModel))
        queue(link<String>(id = "link", handler = { showLabelModel.value = !showLabelModel.value }))
        q(TextField("name", nameModel)
                .setLabel("New Name".model())
                .handleEvent { c, _: NonAjaxResetEvent -> c.clearInput() }
                .required())
        q(button(id = "updateButton", handler = NonAjaxSubmitHandler(
                submit = {},
                error = {}
        )))
        q(button(id = "resetButton", defaultProcessing = false, handler = NonAjaxSubmitHandler(
                submit = {
                    nameModel.value = defaultName
                    showLabelModel.value = defaultShowLabel
                    send(page, Broadcast.BREADTH, NonAjaxResetEvent())
                }
        )))
        q(button(id = "clearButton", defaultProcessing = false, handler = NonAjaxSubmitHandler(
                submit = {
                    nameModel.value = null
                }
        )))
    }

}