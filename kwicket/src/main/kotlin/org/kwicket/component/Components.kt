package org.kwicket.component

import org.apache.wicket.Component
import org.apache.wicket.MarkupContainer
import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.ajax.markup.html.AjaxLink
import org.apache.wicket.ajax.markup.html.form.AjaxButton
import org.apache.wicket.behavior.Behavior
import org.apache.wicket.markup.html.form.Button
import org.apache.wicket.markup.html.form.FormComponent
import org.apache.wicket.markup.html.link.Link
import org.apache.wicket.markup.html.list.ListItem
import org.apache.wicket.markup.html.list.ListView
import org.apache.wicket.model.IModel
import org.apache.wicket.model.LoadableDetachableModel
import org.kwicket.AjaxHandler
import org.kwicket.IAjaxSubmitHandler
import org.kwicket.NonAjaxHandler
import org.kwicket.NonAjaxSubmitHandler
import org.kwicket.behavior.eventHandler
import org.kwicket.model.ldm
import org.kwicket.model.value

/**
 * Queues the [component] in the receiver and returns the [component].
 *
 * @receiver [MarkupContainer] to add [component] to
 * @return the [component] added the receiver
 */
fun <C: Component, M: MarkupContainer> M.q(component: C): C {
    queue(component)
    return component
}

/**
 * Makes the [FormComponent] required and returns it.
 *
 * @receiver [FormComponent] to make required
 * @return the [FormComponent] receiver
 */
fun <C: FormComponent<*>> C.required(): C {
    isRequired = true
    return this
}

/**
 * Specifies that the [Component] is refreshable via ajax.
 *
 * This is done by setting [Component.setOutputMarkupId] to true and setting [Component.setOutputMarkupPlaceholderTag]
 * to the value of [placeholder].
 *
 * @receiver [Component] the refreshable setting is being applied to
 * @param placeholder value to set [Component.setOutputMarkupPlaceholderTag] to; be default it is true
 * @return the [Component]
 */
fun <C: Component> C.updatable(placeholder: Boolean? = null): C {
    outputMarkupId = true
    if (placeholder != null) {
        outputMarkupPlaceholderTag = placeholder
    }
    return this
}

/**
 * Specifies when the [Component] is visible via the [model] value.
 *
 * This is done by adding a [Behavior] that in its [Behavior.onConfigure] method calls the [Component.setVisible]
 * method with the value of the [model].
 *
 * @receiver [Component] the visibility is being set on
 * @param model value specifies whether the component should be visible
 * @return the [Component]
 */
fun <C: Component> C.visibleWhen(model: IModel<Boolean>): C {
    add(org.kwicket.behavior.onConfigure { isVisible = model.value } )
    return this
}

/**
 * Specifies when the [Component] is visible via the lambda expression.
 *
 * This is done by adding a [Behavior] that in its [Behavior.onConfigure] method calls the [Component.setVisible]
 * method with the value of the lambda expression.  The lambda result is cached in a [LoadableDetachableModel] during
 * rendering.
 *
 * @see visibleWhen
 * @receiver [Component] the visibility is being set on
 * @param isVisible lambda expression that specifies whether the component should be visible
 * @return the [Component]
 */
fun <C: Component> C.visibleWhen(isVisible: () -> Boolean): C = visibleWhen(isVisible.ldm())

/**
 * Specifies when the [Component] is enabled via the [model] value.
 *
 * This is done by adding a [Behavior] that in its [Behavior.onConfigure] method calls the [Component.setEnabled]
 * method with the value of the [model].
 *
 * @receiver [Component] that is being enabled/disabled
 * @param model value specifies whether the [Component] should be enabled
 * @return the [Component]
 */
fun <C: Component> C.enabledWhen(model: IModel<Boolean>): C {
    add(org.kwicket.behavior.onConfigure { isEnabled = model.value })
    return this
}

/**
 * Specifies when the [Component] is enabled via the lambda expression.
 *
 * This is done by adding a [Behavior] that in its [Behavior.onConfigure] method calls the [Component.setEnabled]
 * method with the value of the lambda expression.
 *
 * @see enabledWhen
 * @receiver [Component] that is being enabled/disabled
 * @param isEnabled lambda expression that specifies whether the [Component] should be enabled
 * @return the [Component]
 */
fun <C: Component> C.enabledWhen(isEnabled: () -> Boolean): C = enabledWhen(isEnabled.ldm())

/**
 * Returns a new [AjaxLink] instance.  The implementation of the ajax [AjaxLink.onClick] method is specified by the [handler].
 *
 * @param id component identifier
 * @param model nullable model of the button
 * @param handler ajax click handler
 * @return [AjaxLink] instance
 */
fun <T> ajaxLink(id: String, model: IModel<T>? = null, handler: AjaxHandler): AjaxLink<T> {
    return object : AjaxLink<T>(id, model) {
        override fun onClick(target: AjaxRequestTarget) = handler(target)
    }
}

/**
 * Returns a new [Link] instance.  The implementation of the [Link.onClick] method is specified by the [handler].
 *
 * @param id component identifier
 * @param model nullable model of the button
 * @param handler click handler
 * @return [Link] instance
 */
fun <T> link(id: String, model: IModel<T>? = null, handler: NonAjaxHandler): Link<T> {
    return object : Link<T>(id, model) {
        override fun onClick() = handler()
    }
}

/**
 * Returns [Button] instance.  The implementations of the [Button.onSubmit] and [Button.onError]
 * methods are specified by the [handler].
 *
 * @param id component identifier
 * @param model model of the [Button]
 * @param handler submit and error handlers
 * @return [Button] instance
 */
fun button(id: String, model: IModel<String>? = null, handler: NonAjaxSubmitHandler, defaultProcessing: Boolean = true): Button {
    val errorHandler = handler.error
    val submitHandler = handler.submit
    val button = object : Button(id, model) {

        override fun onSubmit() {
            submitHandler()
        }

        override fun onError() {
            errorHandler()
        }
    }
    button.defaultFormProcessing = defaultProcessing
    return button
}

/**
 * Returns an [AjaxButton] instance.  The implementations of the ajax [AjaxButton.onSubmit] and [AjaxButton.onError]
 * methods are specified by the [handler].
 *
 * @param id component identifier
 * @param model model of the [AjaxButton]
 * @param handler ajax submit and error handlers
 * @return [AjaxButton] instance
 */
fun ajaxButton(id: String, model: IModel<String>? = null, handler: IAjaxSubmitHandler, defaultProcessing: Boolean = true): AjaxButton {
    val errorHandler = handler.error
    val submitHandler = handler.submit
    val button = object : AjaxButton(id, model) {
        override fun onSubmit(target: AjaxRequestTarget) = submitHandler(target)
        override fun onError(target: AjaxRequestTarget) = errorHandler(target)
    }
    button.defaultFormProcessing = defaultProcessing
    return button
}

/**
 * Adds an event handler to the receiver component.  The event handler will be invoked if the payload associated
 * with the event is of type [E].
 *
 * @receiver [Component] the event handler is being added to
 * @param handler event handler handles events of type [E]
 * @return the [Component]
 */
inline fun <reified C: Component, reified E> C.handleEvent(crossinline handler: (C, E) -> Unit): C {
    add(eventHandler(handler))
    return this
}

/**
 * Returns a [ListView] instance.  The implementation of the ajax [ListView.populateItem]
 * method is specified by the [populate] lambda.
 *
 * @param id component identifier
 * @param model model of the [ListView]
 * @param populate lambda that supplies the [ListView.populateItem] functionality
 * @param reuseItems whether to reuse items in the [ListView]
 * @return [ListView] instance
 */
fun <T> listView(id: String, model: IModel<List<T>>, reuseItems: Boolean? = null, populate: (ListItem<T>) -> Unit): ListView<T> {
    val listView = object : ListView<T>(id, model) {
        override fun populateItem(item: ListItem<T>) {
            populate(item)
        }
    }
    if (reuseItems != null) {
        listView.reuseItems = reuseItems
    }
    return listView
}