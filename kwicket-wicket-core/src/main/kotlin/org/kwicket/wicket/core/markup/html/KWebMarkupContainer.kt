package org.kwicket.wicket.core.markup.html

import org.apache.wicket.behavior.Behavior
import org.apache.wicket.markup.html.WebMarkupContainer
import org.apache.wicket.model.IModel
import org.kwicket.component.init

/**
 * [WebMarkupContainer] component with named and default constructor arguments.
 *
 * @param id id of the [Component]
 * @param model optional [IModel] of the [Component]
 * @param outputMarkupId optional flag indicating whether an id attribute will be created on the HTML element
 * @param outputMarkupPlaceholderTag optional flag indicating whether an id attribtue will be created on the HTML
 *      element, creating a placeholder id if the component is initially not visible
 * @param visible optional flag indicating whether the [Component] is visible
 * @param enabled optional flag indicating whether the [Component] is enabled
 * @param renderBodyOnly optional flag indicating whether only the [Component]'s HTML will be rendered or whether the
 *      tag the [Component] is attached to will also be rendered
 * @param behaviors [List] of [Behavior]s to add to the [Component]
 */
open class KWebMarkupContainer(id: String,
                               model: IModel<*>? = null,
                               outputMarkupId: Boolean? = null,
                               outputMarkupPlaceholderTag: Boolean? = null,
                               visible: Boolean? = null,
                               enabled: Boolean? = null,
                               renderBodyOnly: Boolean? = null,
                               escapeModelStrings: Boolean? = null,
                               vararg behaviors: Behavior)
    : WebMarkupContainer(id, model) {

    init {
        init(outputMarkupId = outputMarkupId,
                outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
                renderBodyOnly = renderBodyOnly,
                escapeModelStrings = escapeModelStrings,
                visible = visible,
                enabled = enabled,
                behaviors = *behaviors)
    }

}