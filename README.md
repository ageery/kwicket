kWicket: Wicket with Kotlin characteristics
===========================================

The kWicket project provides [Kotlin](https://kotlinlang.org/) idiomatic extensions for the 
[Apache Wicket](http://wicket.apache.org) web framework (versions 8 and up).

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
(e.g., `val nowModel: IModel<LocalDateTime> = { LocalDateTime.now() }.ldm()`)

* `IModel<M> + ((M) -> N))` - Creates a `LoadableDetachableModel<N>` where the value is provided by applying the
lambda to the value of the model (e.g., `val firstNameModel: IModel<String> = personModel + { it.firstName }`)
 
* `IModel<M> + KProperty<M, N>` - Creates an `IModel<N>` where the value is provided by getting the value of the 
property from the value of the model (e.g., `val ageModel: IModel<Int> = personModel + Person::age`)
 
* `IModel<*> + PropChain<M>` -  Creates an `IModel<M>` where the value is provided by applying the chain of properties
to the value of the model (e.g., `val motherName = childModel + PropChain { Person::mother + Person::name }`).

Components
----------

* `Component.q(Component)` - `queue` the child component in the parent component and returns the child component
(e.g., `val nameField: FormComponent<String> = q(TextField("name", nameModel))`).

* `Component.refresh(AjaxRequestTarget?)` - Refreshes the component via ajax; if the target is null, looks up the
current one in the request (e.g., `nameField.refresh(target)`) 

Behaviors
---------
 
* `<C: Component> C.onConfig(handler: (C) -> Unit)`
```kotlin
KLabel(id = "firstName", 
    model = personModel + Person::firstName)
    .onConfig({ it.setVisible = false })
```    
* `VisibleWhen(val isVisible: () -> Boolean)`
```kotlin
KTextField(id = "firstName", 
    model = model + Person::firstName, 
    behaviors = VisibleWhen( { model.value.type == PersonType.INDIVIDUAL } ))
```
* `EnabledWhen(val isEnabled: () -> Boolean)` 
```kotlin
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
```kotlin
val greeting = KLabel(id = "greeting", 
    model = "Hello".model(),  
    outputPlaceholderTag = true, 
    visible = false)`
```

Classes that extend from `FormComponent` have a couple of other named parameters as well:
    
    * required: Boolean? = null
    * label: IModel<String>? = null

For example:
```kotlin
val firstNameField = KTextField(id = "firstName",
    model = personModel + Person::firstName,
    label = "First Name".model(),
    required = true) 
```

In addition to the common named and default parameters that all of the enhanced components share, some classes
also have additional class-specific named parameters (e.g., `KTextField(type: Class<T>? = null)`, 
`KAjaxButton(defaultFormProcessing: Boolean? = null)`).

Enhanced Component List
-----------------------

### Containers
* KWebMarkupContainer
* KPanel
* KGenericPanel
* KFeedbackPanel
* KListView
* LinkPanel
* ButtonPanel

### Links
* KLink
* KAjaxLink
* KBookmarkablePageLink
* KBootstrapAjaxLink

### Buttons
* KButton
* KAjaxButton
* KBootstrapAjaxButton

### Labels
* KLabel
* KMultiLineLabel

### Form Components
* KForm
* KBootstrapForm
* KFormGroup
* KCheckbox
* KTextField
* KTextArea
* InputFormGroup
* SelectFormGroup

### Tables
* KDataTable
* KAjaxFallbackDefaultDataTable
* KAbstractColumn
* KLambdaColumn
* KFilterToolbar
* KNoRecordsToolbar
* LinkColumn
* KTableBehavior
* KSortableDataProvider

### Tabs
* KAbstractTab
* KAjaxBootstrapTabbedPanel

### Modals
* PanelModal
* ModalInfo
* HasModalInfo

### select2
* KSelect2Choice
* KSelect2MultiChoice
* KChoiceProvider
* SimpleChoiceProvider

### Behaviors
* KAjaxEventBehavior
* KThrottleSettings
* KAjaxFormComponentUpdatingBehavior

### Other
* KWebApplication
* KWicketFilter

Usage
-----

The kWicket libraries do not declare run-time dependencies on any Wicket libraries. To use the kWicket libraries,
declare your dependencies on the appropriate Wicket libraries _and_ also add the kWicket dependencies.

`build.gradle`

```groovy
    ...
    repositories {
        mavenCentral()
        maven {
            url 'https://dl.bintray.com/ageery/maven'
        }
    }
    ...
    ext {
        wicketVersion = '8.0.0-M7'
        kWicketVersion = '0.0.1'
    }
    ...
    dependencies {
        compile("org.apache.wicket:wicket-core:${wicketVersion}")
        compile ("org.kwicket:kwicket-core:${kWicketVersion}")
    }
    ...
```

`pom.xml`

```xml
    ...
    <repositories>
        <repository>
          <id>kwicket</id>
          <name>kWicket Repo</name>
          <url>https://dl.bintray.com/ageery/maven</url>
        </repository>
    </repositories>
    ...
    <properties>
        <wicketVersion>8.0.0-M7</wicketVersion>
        <kwicketVersion>0.0.1</kwicketVersion>
    </properties>
    ...
    <dependencies>
        <dependency>
            <groupId>org.apache.wicket</groupId>
            <artifactId>wicket-core</artifactId>
            <version>${wicketVersion}</version>
        </dependency>
        <dependency>
            <groupId>org.kwicket</groupId>
            <artifactId>kwicket-core</artifactId>
            <version>${kwicketVersion}</version>
        </dependency>
    </dependencies>
    ...
```

To get started using kWicket, see the [kWicket Starter](https://github.com/ageery/kwicket-starter) project for a 
simple, working example.

For examples of kWicket usage, see the kwicket-sample module.

Building
--------

To build the project, from the root of the project type `gradlew clean build`.

To run the sample project, from the root of the project type `gradlew bootRun`.