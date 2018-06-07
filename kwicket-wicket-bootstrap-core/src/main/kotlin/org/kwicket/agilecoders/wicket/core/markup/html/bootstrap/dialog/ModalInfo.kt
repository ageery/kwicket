package org.kwicket.agilecoders.wicket.core.markup.html.bootstrap.dialog

import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.Modal.Size
import org.apache.wicket.markup.html.form.Button
import org.apache.wicket.markup.html.form.Form
import org.apache.wicket.model.IModel
import java.io.Serializable

data class ModalInfo(
    val size: Size? = null,
    val escapeHeaderString: Boolean? = null,
    val title: IModel<String>? = null,
    val footerButtons: ((String, Form<*>?) -> List<Button>)? = null,
    val form: Form<*>? = null
) : Serializable