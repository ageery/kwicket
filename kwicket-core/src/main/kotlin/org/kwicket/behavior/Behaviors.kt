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
import org.kwicket.model.value

open class OnConfigureBehavior(val handler: (Component) -> Unit) : Behavior() {

    override fun onConfigure(component: Component) {
        handler(component)
    }

}

inline fun <reified T> onEvent(outputMarkupId: Boolean? = null,
                               onlyWhenVisible: Boolean = true,
                               crossinline handler: (T, Component) -> Unit) =
    onEvent(outputMarkupId = outputMarkupId, handler = handler, guard = { true }, onlyWhenVisible = onlyWhenVisible)

inline fun <reified T> onEvent(outputMarkupId: Boolean? = null,
                               crossinline handler: (T, Component) -> Unit,
                               onlyWhenVisible: Boolean = true,
                               crossinline guard: (T) -> Boolean) =
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

class VisibleWhen(val isVisibleModel: IModel<Boolean>) : OnConfigureBehavior(handler = { c -> c.isVisible = isVisibleModel.value }) {

    constructor(isVisible: () -> Boolean): this(IModel<Boolean> { isVisible() })

}
class EnabledWhen(isEnabledModel: IModel<Boolean>) : OnConfigureBehavior(handler = { c -> c.isEnabled = isEnabledModel.value }) {

    constructor(isEnabled: () -> Boolean): this(IModel<Boolean> { isEnabled() })

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

