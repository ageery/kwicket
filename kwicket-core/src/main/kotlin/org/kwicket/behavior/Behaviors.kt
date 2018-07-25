package org.kwicket.behavior

import org.apache.wicket.Component
import org.apache.wicket.MarkupContainer
import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior
import org.apache.wicket.behavior.Behavior
import org.apache.wicket.event.IEvent
import org.apache.wicket.model.IModel
import org.apache.wicket.util.time.Duration
import org.kwicket.component.refresh
import org.kwicket.model.AsyncModel
import org.kwicket.model.ldm
import org.kwicket.model.value

/**
 * [Behavior] that implements the `onConfigure` method using the supplied [handler] lambda.
 *
 * @property handler lambda that will be used to implement the `onConfigure` method
 */
open class OnConfigureBehavior(val handler: (Component) -> Unit) : Behavior() {
    override fun onConfigure(component: Component) = handler(component)
}

open class OnEventBehavior(val handler: (Component, IEvent<*>) -> Unit) : Behavior() {
    override fun onEvent(component: Component, event: IEvent<*>) = handler(component, event)
}

@Deprecated(
    message = "Use handleEvent instead",
    replaceWith = ReplaceWith(expression = "org.kwicket.behavior.handleEvent")
)
inline fun <reified T> onEvent(
    outputMarkupId: Boolean? = null,
    onlyWhenVisible: Boolean = true,
    crossinline handler: (T, Component) -> Unit
) =
    onEvent(outputMarkupId = outputMarkupId, handler = handler, guard = { true }, onlyWhenVisible = onlyWhenVisible)

@Deprecated(
    message = "Use handleEvent instead",
    replaceWith = ReplaceWith(expression = "org.kwicket.behavior.handleEvent")
)
inline fun <reified T> onEvent(
    outputMarkupId: Boolean? = null,
    onlyWhenVisible: Boolean = true,
    crossinline guard: (T) -> Boolean,
    crossinline handler: (T, Component) -> Unit
) =
    object : Behavior() {

        override fun bind(component: Component) {
            outputMarkupId?.let {
                if (component.outputMarkupId != it) {
                    component.outputMarkupId = it
                }
            }
        }

        override fun onEvent(component: Component, event: IEvent<*>) {
            val payload = event.payload
            if ((!onlyWhenVisible) || component.isVisible) {
                if (payload is T) {
                    if (guard(payload)) {
                        handler(payload, component)
                    }
                }
            }
        }

    }

/**
 * Returns an event-handling [Behavior].
 *
 * The event will be handled by the [Behavior] if the [transform] lambda produces a non-null [T] object from the
 * [P] object.
 *
 * @receiver [Component] to add an event handler to
 * @param P payload of the [IEvent]
 * @param T the type that [P] can be converted into
 * @param onlyWhenVisible only invoke the handler if the associated [Component] is visible; by default, the value is true
 * @param stop do not continue propagating the event; by default the value is false
 * @param dontBroadcastDeeper do not allow the event to propagate any deeper; by default, the value is false
 * @param transform lambda that converts [P] to a nullable [T]
 * @param handler lambda that defines how to handle the event; the lambda parameters are the event (e.g., SaveEvent),
 * a non-null value derived from the event and the component the behavior is associated with
 */
inline fun <reified P, reified T> eventHandler(
    onlyWhenVisible: Boolean = true,
    stop: Boolean = false,
    dontBroadcastDeeper: Boolean = false,
    crossinline transform: (P) -> T?,
    crossinline handler: (P, T, Component) -> Unit
) =
    OnEventBehavior { component, event ->
        val payload = event.payload
        if ((!onlyWhenVisible) || component.isVisible) {
            if (payload is P) {
                transform(payload)?.apply {
                    handler(payload, this, component)
                    if (stop) event.stop()
                    if (dontBroadcastDeeper) event.dontBroadcastDeeper()
                }
            }
        }
    }

/**
 * Adds an event-handling [Behavior] to the @receiver.
 *
 * The event will be handled by the [Behavior] if the [transform] lambda produces a non-null [T] object from the
 * [P] object.
 *
 * A use-case for this method is to deal with [P]s that have nested generics since only the top-level generic
 * is reified.
 *
 * For example:
 *
 * ```
 * handleEvent<SaveEvent<*>, Person>(transform = { it.value as? Person }) { event, person, component -> }
 * ```
 *
 * @receiver [Component] to add an event handler to
 * @param P payload of the [IEvent]
 * @param T the type that [P] can be converted into
 * @param onlyWhenVisible only invoke the handler if the associated [Component] is visible; by default, the value is true
 * @param stop do not continue propagating the event; by default the value is false
 * @param dontBroadcastDeeper do not allow the event to propagate any deeper; by default, the value is false
 * @param transform lambda that converts [P] to a nullable [T]
 * @param handler lambda that defines how to handle the event; the lambda parameters are the event (e.g., SaveEvent),
 * a non-null value derived from the event and the component the behavior is associated with
 */
inline fun <reified P, reified T> Component.handleEvent(
    crossinline transform: (P) -> T?,
    onlyWhenVisible: Boolean = true,
    stop: Boolean = false,
    dontBroadcastDeeper: Boolean = false,
    crossinline handler: (P, T, Component) -> Unit
) {
    add(
        eventHandler(
            onlyWhenVisible = onlyWhenVisible,
            stop = stop,
            dontBroadcastDeeper = dontBroadcastDeeper,
            transform = transform,
            handler = handler
        )
    )
}

/**
 * Adds an event-handling [Behavior] to the @receiver.
 *
 * The event will be handled by the [Behavior] if the payload of the event is of type [P].
 *
 * @receiver [Component] to add an event handler to
 * @param P payload of the [IEvent]
 * @param onlyWhenVisible only invoke the handler if the associated [Component] is visible; by default, the value is true
 * @param stop do not continue propagating the event; by default the value is false
 * @param dontBroadcastDeeper do not allow the event to propagate any deeper; by default, the value is false
 * @param handler lambda that defines how to handle the event; the lambda parameters are the event (e.g., ResetEvent)
 * and the component the behavior is associated with
 */
inline fun <reified P> Component.handleEvent(
    onlyWhenVisible: Boolean = true,
    stop: Boolean = false,
    dontBroadcastDeeper: Boolean = false,
    crossinline handler: (P, Component) -> Unit
) = handleEvent<P, P>(
    transform = { it },
    handler = { p, _, c -> handler(p, c) },
    onlyWhenVisible = onlyWhenVisible,
    stop = stop,
    dontBroadcastDeeper = dontBroadcastDeeper
)

/**
 * [Behavior] that sets a [Component]'s visibility based on the value of the [isVisibleModel].
 *
 * @param isVisibleModel [Boolean] model the value of which is used to set the visibility of the [Component]
 */
class VisibleWhen(isVisibleModel: IModel<Boolean>) :
    OnConfigureBehavior(handler = { c -> c.isVisible = isVisibleModel.value }) {

    /**
     * Creates an instance from a lambda.
     *
     * @param isVisible lambda that will be wrapped in a LoadableDetachableModel
     */
    constructor(isVisible: () -> Boolean) : this(isVisible.ldm())
}

/**
 * [Behavior] that sets whether a [Component] is enabled based on the value of the [isEnabledModel].
 *
 * @param isEnabledModel [Boolean] model the value of which is used to set whether the [Component] is enabled
 */
class EnabledWhen(isEnabledModel: IModel<Boolean>) :
    OnConfigureBehavior(handler = { c -> c.isEnabled = isEnabledModel.value }) {

    /**
     * Creates an instance from a lambda.
     *
     * @param isEnabled lambda that will be wrapped in a LoadableDetachableModel
     */
    constructor(isEnabled: () -> Boolean) : this(isEnabled.ldm())
}

/**
 * Adds a [Behavior] that call the [handler] in the onConfigure method.
 *
 * This method differs from the [OnConfigureBehavior] class in that rather than a generic [Component] being the lambda
 * parameter, the exact type of component that the behavior was added to is the lambda parameter.
 *
 * Example:
 *
 * ```
 *
 * val nameField: FormComponent<*> = ...
 * nameField.onConfig { it.isRequired = true } // the isRequired is available because the lambda component is typed as a FormComponent
 * ```
 *
 * @receiver [Component] to add the [Behavior] to
 * @param handler lambda to call in the onConfigure method; the parameter to the lambda is the [Component] the
 * [Behavior] is being added to
 */
fun <C : Component> C.onConfig(handler: (C) -> Unit) {
    add(object : Behavior() {
        override fun onConfigure(component: Component) {
            super.onConfigure(component)
            @Suppress("UNCHECKED_CAST")
            handler(component as C)
        }
    })
}

// FIXME: move this into a dedicated async file
fun Component.asyncLoad() {
    defaultModel.let { model ->
        if (model is AsyncModel<*>) {
            model.startAsyncLoad()
        }
    }
}

// FIXME: move this into a dedicated async file
class AsyncModelLoadBehavior : Behavior() {

    private fun startAsync(model: IModel<*>) {
        if (model is AsyncModel<*>) {
            model.startAsyncLoad()
        }
    }

    private fun cancelAsync(model: IModel<*>) {
        if (model is AsyncModel<*>) {
            model.cancelAsyncLoad()
        }
    }

    override fun onConfigure(component: Component) {
        if (component.isVisibleInHierarchy) {
            startAsync(component.defaultModel)
            if (component is MarkupContainer) {
                component.visitChildren<Unit> { child, _ ->
                    /*
                     * Because the children's visibility may still change,
                     * we start all of the async models and cancel
                     * them in afterRender if they end up not being visible.
                     */
                    startAsync(child.defaultModel)
                }
            }
        }
    }

    override fun afterRender(component: Component) {
        if (component.isVisibleInHierarchy) {
            if (component is MarkupContainer) {
                component.visitChildren<Unit> { child, _ ->
                    if (!child.isVisibleInHierarchy) {
                        cancelAsync(child.defaultModel)
                    }
                }
            }
        } else {
            cancelAsync(component.defaultModel)
        }
    }
}

// FIXME: can we move these somewhere else?

class ComponentAwareTimerBehavior(dur: Duration, val handler: (AjaxRequestTarget, Component) -> Unit) :
    AjaxSelfUpdatingTimerBehavior(dur) {
    override fun onPostProcessTarget(target: AjaxRequestTarget) = handler(target, component)
}

fun refreshEvery(dur: Duration): Behavior =
    ComponentAwareTimerBehavior(dur = dur, handler = { target, component -> component.refresh(target) })

