kWicket
=======

Wicket with Kotlin characteristics

The kWicket project provides Kotlin idiomatic extensions for the Apache Wicket web framework.

Models
------

* `IModel.value` - Because `object` is a reserved word in Kotlin, an `IModel`'s `object` property has to
be escaped with backticks. To avoid this, the read/write extension property `value` may be used instead
(e.g., `val x = myModel.value`). 
 
* `<T: Serializable?> T.model()` - Creates a `Model<T>` where the value is the `T` object
(e.g., `val myModel: IModel<String> = "Hello".model()`).

* `<L: List<T>> L.listModel()` - Creates a `ListModel<T>` where the value is the `L` object
(e.g., `val myModel: IModel<List<String>> = listOf("here", "there")`).  

* `(() -> Unit).ldm()` - Creates a `LoadableDetachableModel` where the value is provided by the lambda 
(e.g., `val nowModel: IModel<LocalDateTime> = { LocalDateTime.now() }`)

* `IModel<M> + ((M) -> N))` - Creates a `LoadableDetachableModel<N>` where the value is provided by applying the
lambda to the value of the model (e.g., `val firstNameModel: IModel<String> = personModel + { it.firstName }`)
 
* `IModel<M> + KProperty<M, N>` - Creates an `IModel<N>` where the value is provided by getting the value of the 
property from the value of the model (e.g., `val ageModel: IModel<Int> = personModel + Person::age`)
 
* `IModel<*> + PropChain<M>` -  Creates an `IModel<M>` where the value is provided by applying the chain of properties
to the value of the model (e.g., `val motherName = childModel + PropChain { Person::mother + Person::name }`).

Components
----------

* `Component.q(Component)` - `queue` the child component in the parent component and returns the child component
(e.g., `val nameField: FormComponent<String> = q(TextField("name", nameModel))`). Note that the [MarkupContainer.queue]
method returns the container whereas this method returns the component that was added.

* `Component.refresh(AjaxRequestTarget?)` - Refreshes the component via ajax; if the target is null, looks up the
current one in the request (e.g., `nameField.refresh(target)`) 

Behaviors
---------
 
* `<C: Component> C.onConfig(handler: (C) -> Unit)`
```
KLabel(id = "firstName", 
    model = personModel + Person::firstName)
    .onConfig({ it.setVisible = false })
```    
* `VisibleWhen(val isVisible: () -> Boolean)`
```
KTextField(id = "firstName", 
    model = model + Person::firstName, 
    behaviors = VisibleWhen( { model.value.type == PersonType.INDIVIDUAL } ))
```
* `EnabledWhen(val isEnabled: () -> Boolean)` 
```
KTextField(id = "firstName", 
    model = model + Person::firstName, 
    behaviors = EnabledWhen( { user.role.contains(Roles.EDITOR)  } ))
```

Enhanced Components
-------------------

A number of components across several projects have been enhanced for use with Kotlin.

By default, the name of the enhanced component is the name of the "regular" Wicket component (e.g., `Label`) with a 
capital "K" prepended to it (e.g., `KLabel`).

The package name is the package name of the "regular" Wicket component (e.g., `org.apache.wicket.markup.html.basic`), 
with the top-most one or two package removed and replaced with `org.kwicket` (e.g., 
`org.kwicket.wicket.markup.html.basic`). 

All enhanced components make it possible to configure common functionality from the component's constructor, via
named parameters with sensible defaults. The named parameters match the property they affect.

Null parameters do not affect the component. The following are named parameters that are common to every component.

    * id: String
    * model: IModel<*>? = null
    * outputMarkupId: Boolean? = null
    * outputMarkupPlaceholderTag: Boolean? = null
    * visible: Boolean? = null
    * enabled: Boolean? = null
    * renderBodyOnly: Boolean? = null
    * escapeModelStrings: Boolean? = null
    * vararg behaviors: Behavior

For example: 
```
val greeting = KLabel(id = "greeting", 
    model = "Hello".model(),  
    outputPlaceholderTag = true, 
    visible = false)`
```

Classes that extend from `FormComponent` have a couple of other named parameters as well:
    
    * required: Boolean? = null
    * label: IModel<String>? = null

For example:
```
val firstNameField = KTextField(id = "firstName",
    model = personModel + Person::firstName,
    label = "First Name".model(),
    required = true) 
```

In addition to the common named and default parameters that all of the enhanced components share, some classes
also have additional class-specific named parameters (e.g., `KTextField(type: Class<T>? = null)`, 
`KAjaxButton(defaultFormProcessing: Boolean? = null)`). 



