package org.kwicket.wicket.core.markup.html.form.validation

import org.apache.wicket.markup.html.form.Form
import org.apache.wicket.markup.html.form.FormComponent
import org.apache.wicket.markup.html.form.validation.IFormValidator

open class KFormValidator(
    private val dependentFormComponents: () -> Array<FormComponent<*>>,
    private val validate: (Form<*>) -> Unit
) : IFormValidator {
    override fun validate(form: Form<*>) = validate.invoke(form)
    override fun getDependentFormComponents(): Array<FormComponent<*>> = dependentFormComponents()
}