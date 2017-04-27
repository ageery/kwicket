package org.kwicket.sample

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons
import de.agilecoders.wicket.core.markup.html.bootstrap.form.FormGroup
import de.agilecoders.wicket.core.markup.html.bootstrap.form.InputBehavior
import de.agilecoders.wicket.core.markup.html.bootstrap.image.GlyphIconType
import org.apache.wicket.event.Broadcast.BREADTH
import org.apache.wicket.event.Broadcast.BUBBLE
import org.apache.wicket.model.IModel
import org.kwicket.agilecoders.wicket.core.ajax.markup.html.bootstrap.button.KBootstrapAjaxButton
import org.kwicket.agilecoders.wicket.core.ajax.markup.html.bootstrap.form.KBootstrapForm
import org.kwicket.behavior.VisibleWhen
import org.kwicket.component.onEvent
import org.kwicket.component.q
import org.kwicket.model.KPropModel
import org.kwicket.model.PropChain
import org.kwicket.model.model
import org.kwicket.model.value
import org.kwicket.wicket.core.markup.html.form.KTextField
import org.kwicket.wicket.core.markup.html.panel.KPanel

class CustomerEditPanel(id: String, model: IModel<Customer?>, visibleWhen: () -> Boolean)
    : KPanel(id, model, outputMarkupPlaceholderTag = true, behaviors = VisibleWhen(visibleWhen)) {

    init {
        val form = q(KBootstrapForm(id = "form",
                model = model)
                .onEvent({ c, e: ValidationErrorEvent -> e.target.add(c) })
                .onEvent({ c, _: CancelEvent -> c.clearInput() }))
        q(FormGroup("firstNameGroup"))
        q(KTextField(id = "firstNameField",
                model = KPropModel(model, PropChain { +Customer::firstName }),
                label = "First Name".model(),
                required = true,
                behaviors = InputBehavior()))
        q(FormGroup("lastNameGroup"))
        q(KTextField("lastNameField",
                model = KPropModel(model, PropChain { +Customer::lastName }),
                label = "Last Name".model(),
                required = true,
                behaviors = InputBehavior()))
        q(KBootstrapAjaxButton(id = "saveButton",
                model = "Save".model(),
                type = Buttons.Type.Primary,
                icon = GlyphIconType.check,
                onSubmit = { target -> send(this, BUBBLE, SaveEvent(target, model.value!!)) },
                onError = { target -> send(form, BUBBLE, ValidationErrorEvent(target)) }))
        q(KBootstrapAjaxButton(id = "cancelButton",
                model = "Cancel".model(),
                defaultFormProcessing = false,
                type = Buttons.Type.Default,
                icon = GlyphIconType.remove,
                onSubmit = { target -> send(page, BREADTH, CancelEvent(target)) }))
    }

}

