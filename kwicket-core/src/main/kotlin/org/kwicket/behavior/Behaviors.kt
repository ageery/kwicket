package org.kwicket.behavior

import org.apache.wicket.Component
import org.apache.wicket.MarkupContainer
import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior
import org.apache.wicket.behavior.Behavior
import org.apache.wicket.event.IEvent
import org.apache.wicket.markup.html.form.FormComponent
import org.apache.wicket.model.IModel
import org.apache.wicket.util.time.Duration
import org.kwicket.component.refresh
import org.kwicket.model.AsyncModel
import org.kwicket.model.value

open class OnConfigureBehavior(val handler: (Component) -> Unit) : Behavior() {

    override fun onConfigure(component: Component) {
        handler(component)
    }

}

@Deprecated(message = "Use handleEvent instead", replaceWith = ReplaceWith(expression = "org.kwicket.behavior.handleEvent"))
inline fun <reified T> onEvent(
    outputMarkupId: Boolean? = null,
    onlyWhenVisible: Boolean = true,
    crossinline handler: (T, Component) -> Unit
) =
    onEvent(outputMarkupId = outputMarkupId, handler = handler, guard = { true }, onlyWhenVisible = onlyWhenVisible)

@Deprecated(message = "Use handleEvent instead", replaceWith = ReplaceWith(expression = "org.kwicket.behavior.handleEvent"))
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

inline fun <reified P, reified E> Component.handleEvent(
    crossinline transform: (P) -> E?,
    onlyWhenVisible: Boolean = true,
    stop: Boolean = false,
    dontBroadcastDeeper: Boolean = false,
    crossinline handler: (P, E, Component) -> Unit
) =
    add(
        object : Behavior() {

            override fun onEvent(component: Component, event: IEvent<*>) {
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
        }

    )

inline fun <reified E> Component.handleEvent(
    onlyWhenVisible: Boolean = true,
    stop: Boolean = false,
    dontBroadcastDeeper: Boolean = false,
    crossinline handler: (E, Component) -> Unit
) = handleEvent<E, E>(transform = { it }, handler = { p, _, c -> handler(p, c) }, onlyWhenVisible = onlyWhenVisible,
    stop = stop, dontBroadcastDeeper = dontBroadcastDeeper)

class VisibleWhen(val isVisibleModel: IModel<Boolean>) :
    OnConfigureBehavior(handler = { c -> c.isVisible = isVisibleModel.value }) {

    constructor(isVisible: () -> Boolean) : this(IModel<Boolean> { isVisible() })

}

class EnabledWhen(isEnabledModel: IModel<Boolean>) :
    OnConfigureBehavior(handler = { c -> c.isEnabled = isEnabledModel.value }) {

    constructor(isEnabled: () -> Boolean) : this(IModel<Boolean> { isEnabled() })

}

class RequiredWhen(isRequiredModel: IModel<Boolean>) : OnConfigureBehavior(handler = { c ->

    if (c is FormComponent<*>) {
        c.isRequired = isRequiredModel.value
    }

}) {

    constructor(isRequired: () -> Boolean) : this(IModel<Boolean> { isRequired() })

}

fun <C : Component> C.onConfig(handler: (C) -> Unit): C {
    add(object : Behavior() {
        override fun onConfigure(component: Component) {
            super.onConfigure(component)
            @Suppress("UNCHECKED_CAST")
            handler(component as C)
        }
    })
    return this
}

fun Component.asyncLoad() {
    defaultModel.let { model ->
        if (model is AsyncModel<*>) {
            model.startAsyncLoad()
        }
    }
}

//class AsyncModelLoadBehavior : Behavior() {
//
//    override fun onConfigure(component: Component) {
//        super.onConfigure(component)
//        if (component.isVisibleInHierarchy) {
//            component.asyncLoad()
//        }
//    }
//
//}

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

class ComponentAwareTimerBehavior(dur: Duration, val handler: (AjaxRequestTarget, Component) -> Unit) :
    AjaxSelfUpdatingTimerBehavior(dur) {
    override fun onPostProcessTarget(target: AjaxRequestTarget) = handler(target, component)
}

fun refreshEvery(dur: Duration): Behavior =
    ComponentAwareTimerBehavior(dur = dur, handler = { target, component -> component.refresh(target) })

