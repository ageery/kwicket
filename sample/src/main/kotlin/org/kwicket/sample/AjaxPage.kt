package org.kwicket.sample

import org.apache.wicket.Component
import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.event.Broadcast
import org.apache.wicket.model.IModel
import org.kwicket.behavior.EnabledWhen
import org.kwicket.behavior.VisibleWhen
import org.kwicket.component.onEvent
import org.kwicket.component.q
import org.kwicket.model.ldm
import org.kwicket.model.model
import org.kwicket.model.value
import org.kwicket.wicket.core.ajax.form.KAjaxButton
import org.kwicket.wicket.core.ajax.form.KAjaxFormComponentUpdatingBehavior
import org.kwicket.wicket.core.ajax.markup.html.KAjaxLink
import org.kwicket.wicket.core.markup.html.basic.KLabel
import org.kwicket.wicket.core.markup.html.form.KCheckbox
import org.kwicket.wicket.core.markup.html.form.KForm
import org.kwicket.wicket.core.markup.html.form.KTextField
import org.kwicket.wicket.core.markup.html.panel.KFeedbackPanel
import org.wicketstuff.annotation.mount.MountPath
import java.io.Serializable

data class Address(var street: String, var city: String? = null) : Serializable

sealed class SampleAjaxEvent(val target: AjaxRequestTarget) {
    class AjaxResetEvent(target: AjaxRequestTarget) : SampleAjaxEvent(target)
    class AjaxChangeEvent(target: AjaxRequestTarget, val successful: Boolean) : SampleAjaxEvent(target)
}

class AjaxRefreshEvent(val target: AjaxRequestTarget)

val refreshOnSampleAjaxEventHandler: (C: Component, SampleAjaxEvent) -> Unit = { c, e -> e.target.add(c) }
val refreshOnSuccessHandler: (C: Component, SampleAjaxEvent.AjaxChangeEvent) -> Unit = { c, e -> if (e.successful) e.target.add(c) }
val refreshOnAjaxResetHandler: (C: Component, SampleAjaxEvent.AjaxResetEvent) -> Unit = { c, e -> e.target.add(c) }

@MountPath("ajax")
class AjaxPage : BasePage() {

    companion object {
        val defaultShowLabel: Boolean = true
        val defaultName: String = "test"
    }

    init {

        val nameModel = defaultName.model<String?>()
        val showLabelModel: IModel<Boolean> = defaultShowLabel.model()

        q(KForm(id = "form", model = nameModel))
        q(KFeedbackPanel(id = "feedback",
                outputMarkupPlaceholderTag = true)
                .onEvent(refreshOnSampleAjaxEventHandler))
        val nameLabel = q(KLabel(id = "nameLabel",
                model = { nameModel.value ?: "<none>" }.ldm(),
                outputMarkupPlaceholderTag = true,
                behaviors = VisibleWhen { showLabelModel.value })
                .onEvent(refreshOnSuccessHandler)
                .onEvent(refreshOnAjaxResetHandler))
        val linkLabel = q(KLabel(id = "linkLabel",
                model = { if (showLabelModel.value) "Hide" else "Show" }.ldm(),
                outputMarkupId = true)
                .onEvent(refreshOnSuccessHandler)
                .onEvent(refreshOnAjaxResetHandler))
        val checkbox = q(KCheckbox(id = "check",
                model = showLabelModel,
                outputMarkupId = true))
        q(KAjaxLink<Any>(id = "link",
                onClick = { target, _ ->
                    showLabelModel.value = !showLabelModel.value
                    target.add(nameLabel, linkLabel, checkbox)
                }))
        q(KTextField(id = "name",
                model = nameModel,
                label = "New Name".model(),
                required = true,
                outputMarkupId = true,
                behaviors = KAjaxFormComponentUpdatingBehavior(event = "change",
                        onUpdate = { target -> send(page, Broadcast.BREADTH, AjaxRefreshEvent(target)) }))
                .onEvent({ c: KTextField<*>, e: SampleAjaxEvent.AjaxResetEvent ->
                    c.clearInput()
                    e.target.add(c)
                }))
        q(KAjaxButton(id = "updateButton",
                onSubmit = { target -> send(page, Broadcast.BREADTH, SampleAjaxEvent.AjaxChangeEvent(target, true)) },
                onError = { target -> send(page, Broadcast.BREADTH, SampleAjaxEvent.AjaxChangeEvent(target, false)) }
        ))
        q(KAjaxButton(id = "resetButton",
                defaultFormProcessing = false,
                outputMarkupId = true,
                behaviors = EnabledWhen { nameModel.value != defaultName },
                onSubmit = { target ->
                    nameModel.value = defaultName
                    showLabelModel.value = defaultShowLabel
                    send(page, Broadcast.BREADTH, SampleAjaxEvent.AjaxResetEvent(target))
                })
                .onEvent({ c, e: AjaxRefreshEvent -> e.target.add(c) })
                .onEvent({ c, e: SampleAjaxEvent.AjaxResetEvent -> e.target.add(c) }))
        q(KAjaxButton(id = "clearButton",
                defaultFormProcessing = false,
                outputMarkupId = true,
                onSubmit = { target ->
                    nameModel.value = null
                    send(page, Broadcast.BREADTH, SampleAjaxEvent.AjaxResetEvent(target))
                })
                .onEvent({ c, e: SampleAjaxEvent.AjaxResetEvent -> e.target.add(c) }))
    }

}