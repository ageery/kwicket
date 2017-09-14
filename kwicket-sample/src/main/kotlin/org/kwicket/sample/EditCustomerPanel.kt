package org.kwicket.sample

import de.agilecoders.wicket.core.markup.html.bootstrap.form.InputBehavior
import org.apache.wicket.event.Broadcast
import org.apache.wicket.markup.html.form.Form
import org.apache.wicket.model.IModel
import org.apache.wicket.validation.validator.StringValidator.maximumLength
import org.kwicket.agilecoders.wicket.core.ajax.markup.html.bootstrap.button.KBootstrapAjaxButton
import org.kwicket.agilecoders.wicket.core.ajax.markup.html.bootstrap.form.InputFormGroup
import org.kwicket.agilecoders.wicket.core.ajax.markup.html.bootstrap.form.KBootstrapForm
import org.kwicket.agilecoders.wicket.core.ajax.markup.html.bootstrap.form.SelectFormGroup
import org.kwicket.agilecoders.wicket.core.markup.html.bootstrap.dialog.HasModalInfo
import org.kwicket.agilecoders.wicket.core.markup.html.bootstrap.dialog.ModalInfo
import org.kwicket.behavior.onConfig
import org.kwicket.component.q
import org.kwicket.model.ldm
import org.kwicket.model.model
import org.kwicket.model.plus
import org.kwicket.model.value
import org.kwicket.sample.Address.Companion.maxCityLength
import org.kwicket.sample.Customer.Companion.maxFirstNameLength
import org.kwicket.sample.Customer.Companion.maxLastNameLength
import org.kwicket.wicket.core.markup.html.form.KTextField
import org.kwicket.wicket.core.markup.html.panel.KPanel
import org.kwicket.wicketstuff.select2.KSelect2Choice
import org.kwicket.wicketstuff.select2.SimpleChoiceProvider
import kotlin.reflect.KProperty1

class EditCustomerPanel(id: String, model: IModel<EditCustomer>) : HasModalInfo, KPanel(id = id, model = model) {

    val form: Form<EditCustomer>

    init {
        form = q(KBootstrapForm(id = "form", model = model))
        q(InputFormGroup(id = "firstName",
                field = {
                    KTextField(id = it,
                            label = "First Name".model(),
                            required = true,
                            model = model + EditCustomer::firstName,
                            behaviors = *arrayOf(InputBehavior(), maximumLength(maxFirstNameLength)))
                }))
        q(InputFormGroup(id = "lastName",
                field = {
                    KTextField(id = it,
                            label = "Last Name".model(),
                            required = true,
                            model = model + EditCustomer::lastName,
                            behaviors = *arrayOf(InputBehavior(), maximumLength(maxLastNameLength)))
                }))
        q(InputFormGroup(id = "age",
                field = {
                    KTextField(id = it,
                            label = "Age".model(),
                            type = Int::class.javaObjectType,
                            model = model + EditCustomer::age,
                            behaviors = InputBehavior())
                }))
        q(InputFormGroup(id = "city",
                field = {
                    KTextField(id = it,
                            label = "City".model(),
                            required = true,
                            model = model + EditCustomer::city,
                            behaviors = *arrayOf(InputBehavior(), maximumLength(maxCityLength)))
                }))
        q(SelectFormGroup(id = "country",
                field = {
                    KSelect2Choice(id = it,
                            label = "Country".model(),
                            required = true,
                            width = "100%",
                            closeOnSelect = true,
                            model = model + EditCustomer::country,
                            choiceProvider = SimpleChoiceProvider(toIdValue = Country::name,
                                    toDisplayValue = Country::displayName,
                                    allChoices = { Country.values().asSequence() },
                                    idToValue = Country::valueOf,
                                    itemsPerPage = 10)
                    )

                }))
    }

    override val modalInfo = ModalInfo(title = { "${if (model.value.id == null) "Add" else "Edit"} Customer" }.ldm(),
            footerButtons = {
                listOf(
                        KBootstrapAjaxButton(id = it,
                                model = "Save".model(),
                                form = form,
                                onSubmit = { target, button -> send(button, Broadcast.BUBBLE, SaveEvent(target = target, content = model.value)) },
                                onError = { target, _ -> target.add(this) }),
                        KBootstrapAjaxButton(id = it,
                                model = "Cancel".model(),
                                defaultFormProcessing = false,
                                form = form,
                                onSubmit = { target, button -> send(button, Broadcast.BUBBLE, CancelEvent(target)) }))
            })

}