package org.kwicket.component

import org.apache.wicket.Component
import org.apache.wicket.MarkupContainer
import org.apache.wicket.Page
import org.apache.wicket.WicketRuntimeException
import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.behavior.Behavior
import org.apache.wicket.markup.html.form.FormComponent
import org.apache.wicket.model.IModel
import org.apache.wicket.request.mapper.parameter.PageParameters
import org.apache.wicket.validation.IValidator
import kotlin.reflect.KClass

/**
 * Returns a non-null [AjaxRequestTarget].
 *
 * If [target] is not null, [target] is returned.
 *
 * Otherwise, the active [AjaxRequestTarget] is looked up in the request cycle. If there is no non-null
 * [AjaxRequestTarget] available, throws an exception.
 *
 * @receiver context in which to look up the [AjaxRequestTarget] (if necessary)
 * @return non-null [AjaxRequestTarget]
 * @throws WicketRuntimeException if [target] is null and there is no active [AjaxRequestTarget]
 * @param target perhaps null [AjaxRequestTarget]
 */
fun Component.target(target: AjaxRequestTarget?): AjaxRequestTarget =
    target ?: requestCycle.find(AjaxRequestTarget::class.java)
        .orElseThrow { WicketRuntimeException("No AjaxRequestTarget found") }

/**
 * Queues the [component] in the receiver and returns the [component], allowing a [Component] to be queued into a
 * parent [MarkupContainer] and also assigned to a variable in one line of code (e.g., `val c = q(MyComponent("id")`).
 *
 * Note that the return type is different from [MarkupContainer.add] and [MarkupContainer.queue] methods which return
 * the [MarkupContainer] that the component was added to, not the component that was added. This is because add and
 * queue are vararg whereas this method takes exactly one [Component].
 *
 * @receiver [MarkupContainer] to add the [component] to
 * @return the [component] that was added to the receiver
 */
fun <C : Component, M : MarkupContainer> M.q(component: C): C {
    queue(component)
    return component
}

/**
 * Adds the [Component] to the [target].
 *
 * If [target] is null, the active [AjaxRequestTarget] is looked up in the current [RequestCycle].
 *
 * If no active [AjaxRequestTarget] is found, a [WicketRuntimeException] is thrown.
 *
 * @receiver [Component] to refresh by adding to the [target]
 * @param target nullable [AjaxRequestTarget] to be used for adding/refreshing the @receiver
 * @throws WicketRuntimeException if [target] is null and there is active [AjaxRequestTarget] in the [RequestCycle]
 */
fun <C : Component> C.refresh(target: AjaxRequestTarget? = null) = target(target = target).add(this)

/**
 * Initializes a [Component].
 *
 * All parameters are optional; they are nullable and have default values of null so only ones that are of interest
 * need to be specified.
 *
 * Only non-null parameter values cause any change to the [Component].
. *
 * @param outputMarkupId sets whether an id is added to the markup if [outputMarkupId] is not null
 * @param outputMarkupPlaceholderTag sets whether a placeholder id is added to markup if [outputMarkupPlaceholderTag] is not null
 * @param visible sets whether the [Component] is visible if [visible] is not null
 * @param enabled sets whether the [Component] is enabled if [enabled] is not null
 * @param escapeModelStrings sets whether the [IModel] String values are to be HTML escaped is enabled
 *      if [escapeModelStrings] is not null
 * @param renderBodyOnly sets whether to only output the generated content or whether to also include the containing tag
 *      if [renderBodyOnly] is not null
 * @param behaviors [List] of [Behavior]s to add to the [Component]
 *
 * @receiver the [Component] to initialize
 * @return the initialized [Component]
 */
fun <C : Component> C.init(
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    escapeModelStrings: Boolean? = null,
    renderBodyOnly: Boolean? = null,
    behaviors: List<Behavior>? = null
): C {
    renderBodyOnly?.let { this.renderBodyOnly = it }
    outputMarkupId?.let { this.outputMarkupId = it }
    outputMarkupPlaceholderTag?.let { this.outputMarkupPlaceholderTag = it }
    visible?.let { this.isVisible = it }
    enabled?.let { this.isEnabled = it }
    escapeModelStrings?.let { this.escapeModelStrings = it }
    behaviors?.let { if (it.isNotEmpty()) add(*it.toTypedArray()) }
    return this
}

/**
 * Initialize a [FormComponent].
 *
 * All parameters are optional; they are nullable and have default values of null so only ones that are of interest
 * need to be specified.
 *
 * Only non-null parameter values cause any change to the [FormComponent].
 *
 * @param required sets whether the [FormComponent] requires a value if [required] is not null
 * @param label sets the [IModel] for the label of the [FormComponent] if [label] is not null
 * @param outputMarkupId sets whether an id is added to the markup if [outputMarkupId] is not null
 * @param outputMarkupPlaceholderTag sets whether a placeholder id is added to markup if [outputMarkupPlaceholderTag] is not null
 * @param visible sets whether the [FormComponent] is visible if [visible] is not null
 * @param enabled sets whether the [FormComponent] is enabled if [enabled] is not null
 * @param escapeModelStrings sets whether the [IModel] String values are to be HTML escaped is enabled
 *      if [escapeModelStrings] is not null
 * @param renderBodyOnly sets whether to only output the generated content or whether to also include the containing tag
 *      if [renderBodyOnly] is not null
 * @param behaviors [List] of [Behavior]s to add to the [FormComponent]
 * @receiver the [FormComponent] to initialize
 * @return the initialized [FormComponent]
 */
fun <T, C : FormComponent<T>> C.init(
    required: Boolean? = null,
    label: IModel<String>? = null,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    escapeModelStrings: Boolean? = null,
    renderBodyOnly: Boolean? = null,
    validators: List<IValidator<in T>>? = null,
    behaviors: List<Behavior>? = null
): C {
    (this as Component).init(
        outputMarkupId = outputMarkupId,
        outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
        visible = visible,
        enabled = enabled,
        escapeModelStrings = escapeModelStrings,
        renderBodyOnly = renderBodyOnly,
        behaviors = behaviors
    )
    required?.let { this.isRequired = it }
    label?.let { this.label = it }
    validators?.let { it.forEach { validator -> add(validator) } }
    return this
}

/**
 * Adds a [Behavior] to a [Component]
 */
operator fun <T: Component> T.plus(behavior: Behavior): T {
    this.add(behavior)
    return this
}

fun <P: Page> Component.setResponsePage(page: KClass<P>, params: PageParameters? = null) = setResponsePage(page.java, params)
