package org.kwicket.wicket.core.markup.html.form.upload

import org.apache.wicket.behavior.Behavior
import org.apache.wicket.markup.html.form.upload.FileUpload
import org.apache.wicket.markup.html.form.upload.FileUploadField
import org.apache.wicket.model.IModel
import org.kwicket.component.init

open class KFileUploadField(id: String,
                            model: IModel<MutableList<FileUpload>?>,
                            required: Boolean? = null,
                            outputMarkupId: Boolean? = null,
                            outputMarkupPlaceholderTag: Boolean? = null,
                            label: IModel<String>? = null,
                            visible: Boolean? = null,
                            enabled: Boolean? = null,
                            vararg behaviors: Behavior)
    : FileUploadField(id, model) {

    init {
        init(required = required,
                label = label,
                outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
                outputMarkupId = outputMarkupId,
                visible = visible,
                enabled = enabled,
                behaviors = *behaviors)
    }

}