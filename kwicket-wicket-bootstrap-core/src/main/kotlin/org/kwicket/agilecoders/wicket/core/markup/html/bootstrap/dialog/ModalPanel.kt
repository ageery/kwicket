package org.kwicket.agilecoders.wicket.core.markup.html.bootstrap.dialog

import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.Modal.Size
import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.Modal.Size.Medium
import org.apache.wicket.MarkupContainer
import org.apache.wicket.model.IModel
import org.kwicket.wicket.core.markup.html.panel.KPanel

open class ModalPanel(id: String,
                         model: IModel<*>,
                         val size: Size = Medium,
                         val title: IModel<String>? = null,
                         val escapeHeaderString: Boolean? = null)
    : KPanel(id = id, model = model) {

    open val footerButtons: ((String) -> List<MarkupContainer>)? = null

}