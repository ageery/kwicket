package org.kwicket.wicket.core.markup.html.basic

import org.apache.wicket.behavior.Behavior
import org.apache.wicket.markup.html.basic.MultiLineLabel
import org.apache.wicket.model.IModel
import org.kwicket.component.init

/**
 * [MultiLineLabel] with named and default constructor arguments.
 *
 * @constructor creates a [MultiLineLabel] component
 * @param id non-null id of the component
 * @param model non-null model of the component
 * @param outputMarkupId whether the component will output an id attribute into the markup; if null (the default), uses the Wicket default for the param (false)
 * @param outputMarkupPlaceholderTag whether a placeholder tag should be rendered; if null (the default), uses the Wicket default for the param (false)
 * @param visible whether the component is visible; if null (the default), uses the Wicket default for the param (true)
 * @param enabled whether the component is enabled; if null (the default), uses the Wicket default for the param (true)
 * @param escapeModelStrings whether model strings should be escaped; if null (the default), uses the Wicket default for the param (false)
 * @param renderBodyOnly whether only the body of the component should be rendered (and not also the component tag); ; if null (the default), uses the Wicket default for the param (false)
 * @param behaviors [List] of [Behavior]s to add to the component; if null (the default), no behaviors are added to the component
 */
open class KMultiLineLabel(
    id: String,
    model: IModel<String>,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    escapeModelStrings: Boolean? = null,
    renderBodyOnly: Boolean? = null,
    behaviors: List<Behavior>? = null
) : MultiLineLabel(id, model) {

    /**
     * @constructor creates a [MultiLineLabel] component
     * @param id non-null id of the component
     * @param model non-null model of the component
     * @param outputMarkupId whether the component will output an id attribute into the markup; if null (the default), uses the Wicket default for the param (false)
     * @param outputMarkupPlaceholderTag whether a placeholder tag should be rendered; if null (the default), uses the Wicket default for the param (false)
     * @param visible whether the component is visible; if null (the default), uses the Wicket default for the param (true)
     * @param enabled whether the component is enabled; if null (the default), uses the Wicket default for the param (true)
     * @param escapeModelStrings whether model strings should be escaped; if null (the default), uses the Wicket default for the param (false)
     * @param renderBodyOnly whether only the body of the component should be rendered (and not also the component tag); ; if null (the default), uses the Wicket default for the param (false)
     * @param behavior [Behavior] to add to the component
     */
    constructor(
        id: String,
        model: IModel<String>,
        outputMarkupId: Boolean? = null,
        outputMarkupPlaceholderTag: Boolean? = null,
        visible: Boolean? = null,
        enabled: Boolean? = null,
        escapeModelStrings: Boolean? = null,
        renderBodyOnly: Boolean? = null,
        behavior: Behavior
    ) : this(
        id = id,
        model = model,
        outputMarkupId = outputMarkupId,
        outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
        visible = visible,
        enabled = enabled,
        escapeModelStrings = escapeModelStrings,
        renderBodyOnly = renderBodyOnly,
        behaviors = listOf(behavior)
    )

    init {
        init(
            outputMarkupId = outputMarkupId,
            outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
            visible = visible,
            enabled = enabled,
            escapeModelStrings = escapeModelStrings,
            renderBodyOnly = renderBodyOnly,
            behaviors = behaviors
        )
    }

}
