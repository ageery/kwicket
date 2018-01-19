package org.kwicket.agilecoders.wicket.core.ajax.markup.html.bootstrap.form

import de.agilecoders.wicket.core.markup.html.bootstrap.form.FormGroup
import org.apache.wicket.behavior.Behavior
import org.apache.wicket.markup.html.form.FormComponent
import org.kwicket.component.q
import org.kwicket.wicket.core.markup.html.panel.KPanel

class SelectFormGroup(id: String,
                      group: (String) -> FormGroup = { KFormGroup(id = it) },
                      field: (String) -> FormComponent<*>,
                      outputMarkupId: Boolean? = null,
                      outputMarkupPlaceholderTag: Boolean? = null,
                      visible: Boolean? = null,
                      enabled: Boolean? = null,
                      behaviors: List<Behavior>? = null)
    : KPanel(id = id,
        outputMarkupId = outputMarkupId,
        outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
        visible = visible,
        enabled = enabled,
        behaviors = behaviors) {

    init {
        q(group("group"))
        q(field("field"))
    }

}