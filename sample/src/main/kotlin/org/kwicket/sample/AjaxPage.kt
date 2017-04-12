package org.kwicket.sample

import org.apache.wicket.event.Broadcast
import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.markup.html.form.CheckBox
import org.apache.wicket.markup.html.form.Form
import org.apache.wicket.markup.html.form.TextField
import org.apache.wicket.markup.html.panel.FeedbackPanel
import org.kwicket.AjaxSubmitHandler
import org.kwicket.component.ajaxButton
import org.kwicket.component.ajaxLink
import org.kwicket.component.handleEvent
import org.kwicket.component.q
import org.kwicket.component.required
import org.kwicket.component.updatable
import org.kwicket.component.visibleWhen
import org.kwicket.model.model
import org.kwicket.model.value
import org.wicketstuff.annotation.mount.MountPath

@MountPath("ajax")
class AjaxPage : BasePage() {

    init {
        val defaultName: String? = "test"
        val defaultShowLabel = true
        val showLabelModel = defaultShowLabel.model()
        val nameModel = defaultName.model<String?>()

        q(Form("form", nameModel))
        q(FeedbackPanel("feedback")
                .updatable(placeholder = true)
                .handleEvent(refreshOnAjaxEventHandler))
        val nameLabel = q(Label("nameLabel", { nameModel.value ?: "<none>" })
                .visibleWhen(showLabelModel)
                .updatable(placeholder = true)
                .handleEvent(refreshOnSuccessHandler)
                .handleEvent(refreshOnAjaxResetHandler))
        val linkLabel = q(Label("linkLabel", { if (showLabelModel.value) "Hide" else "Show" })
                .updatable()
                .handleEvent(refreshOnSuccessHandler)
                .handleEvent(refreshOnAjaxResetHandler))
        val checkbox = q(CheckBox("check", showLabelModel)
                .updatable())
        queue(ajaxLink<String>(id = "link", handler = { target ->
            showLabelModel.value = !showLabelModel.value
            target.add(nameLabel, linkLabel, checkbox)
        }))
        q(TextField("name", nameModel)
                .setLabel("New Name".model())
                .apply {
                    required()
                    updatable()
                    handleEvent { c, e: AjaxResetEvent ->
                        this.clearInput()
                        e.target.add(c)
                    }
                })
        q(ajaxButton(id = "updateButton", handler = AjaxSubmitHandler(
                submit = { target -> send(page, Broadcast.BREADTH, AjaxChangeEvent(target, true)) },
                error = { target -> send(page, Broadcast.BREADTH, AjaxChangeEvent(target, false)) }
        )))
        q(ajaxButton(id = "resetButton", defaultProcessing = false, handler = AjaxSubmitHandler(
                submit = { target ->
                    nameModel.value = defaultName
                    showLabelModel.value = defaultShowLabel
                    send(page, Broadcast.BREADTH, AjaxResetEvent(target))
                }
        )))
        q(ajaxButton(id = "clearButton", defaultProcessing = false, handler = AjaxSubmitHandler(
                submit = { target ->
                    nameModel.value = null
                    send(page, Broadcast.BREADTH, AjaxResetEvent(target))
                }
        )))
    }

}