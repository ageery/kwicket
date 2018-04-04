package org.kwicket.generator

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.LambdaTypeName
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.ParameterizedTypeName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.get
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeVariableName
import com.squareup.kotlinpoet.WildcardTypeName
import com.squareup.kotlinpoet.WildcardTypeName.Companion.subtypeOf
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.asTypeName
import org.apache.wicket.Component
import org.apache.wicket.Page
import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.ajax.markup.html.AjaxLink
import org.apache.wicket.ajax.markup.html.form.AjaxButton
import org.apache.wicket.behavior.Behavior
import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.markup.html.form.Button
import org.apache.wicket.markup.html.form.CheckBox
import org.apache.wicket.markup.html.form.DropDownChoice
import org.apache.wicket.markup.html.form.Form
import org.apache.wicket.markup.html.form.IChoiceRenderer
import org.apache.wicket.markup.html.form.TextArea
import org.apache.wicket.markup.html.form.TextField
import org.apache.wicket.markup.html.form.validation.IFormValidator
import org.apache.wicket.markup.html.link.BookmarkablePageLink
import org.apache.wicket.markup.html.link.Link
import org.apache.wicket.markup.html.panel.GenericPanel
import org.apache.wicket.model.IModel
import org.apache.wicket.util.file.Files
import java.io.File
import java.nio.file.Paths
import kotlin.reflect.KClass

private val componentParamName = "component"

// there are two types of handlers: handlers where the value comes from an external source and handlers where the value comes from an internal source
// is there a way to unify these two -- maybe by passing in the component instead of the model value?
// or are they really just different?

enum class ComponentParamType(val typeFactory: (TypeName?) -> TypeName) {
    StringParamType({ String::class.asTypeName() }),
    ModelParamType({ get(IModel::class.asTypeName(), it ?: subtypeOf(Any::class)) }),
    ListParamType({ get(List::class.asTypeName(), it!!) }),
    RendererParamType({ get(IChoiceRenderer::class.asTypeName(), it!!) }),
    ClassParamType({ get(Class::class.asTypeName(), it!!) }),
    BooleanParamType({ Boolean::class.asTypeName() }),
    NonAjaxHandlerParamType({
        LambdaTypeName.get(
            returnType = Unit::class.asClassName(),
            parameters = listOf(
                ParameterSpec.builder(
                    name = componentParamName,
                    type = it ?: Component::class.asTypeName()
                ).build()
            )
        )
    }),
    AjaxHandlerParamType({
        LambdaTypeName.get(
            returnType = Unit::class.asClassName(),
            parameters = listOf(
                ParameterSpec.builder(name = "target", type = AjaxRequestTarget::class.asTypeName()).build(),
                ParameterSpec.builder(name = componentParamName, type = it ?: Component::class.asTypeName()).build()
            )
        )
    }),
    FormParamType({ get(Form::class.asTypeName(), it ?: subtypeOf(Any::class)) }),
}

enum class ComponentParam(
    val paramName: String,
    val type: ComponentParamType,
    val defaultNullable: Boolean = true,
    val constraintTypeName: TypeName? = null
) {
    IdParam(paramName = "id", type = ComponentParamType.StringParamType, defaultNullable = false),
    ModelParam(paramName = "model", type = ComponentParamType.ModelParamType),
    LabelParam(
        paramName = "label",
        type = ComponentParamType.ModelParamType,
        constraintTypeName = String::class.asTypeName()
    ),
    OutputMarkupIdParam(paramName = "outputMarkupId", type = ComponentParamType.BooleanParamType),
    OutputMarkupPlaceholderIdParam(
        paramName = "outputMarkupPlaceholderTag",
        type = ComponentParamType.BooleanParamType
    ),
    VisibleParam(paramName = "visible", type = ComponentParamType.BooleanParamType),
    EnabledParam(paramName = "enabled", type = ComponentParamType.BooleanParamType),
    EscapeModelStringsParam(paramName = "escapeModelStrings", type = ComponentParamType.BooleanParamType),
    RenderBodyOnlyParam(paramName = "renderBodyOnly", type = ComponentParamType.BooleanParamType),
    RequiredParam(paramName = "required", type = ComponentParamType.BooleanParamType),
    NullValidParam(paramName = "nullValid", type = ComponentParamType.BooleanParamType),
    DefaultFormProcessingParam(paramName = "defaultFormProcessing", type = ComponentParamType.BooleanParamType),
    BehaviorsParam(
        paramName = "behaviors",
        type = ComponentParamType.ListParamType,
        constraintTypeName = Behavior::class.asTypeName()
    ),
    TypeParam(paramName = "type", type = ComponentParamType.ClassParamType),
    FormParam(paramName = "form", type = ComponentParamType.FormParamType, constraintTypeName = subtypeOf(Any::class)),
    PageClassParam(
        paramName = "page",
        type = ComponentParamType.ClassParamType,
        constraintTypeName = subtypeOf(Page::class)
    ),
    NonAjaxClickHandlerParam(paramName = "onClick", type = ComponentParamType.NonAjaxHandlerParamType),
    AjaxClickHandlerParam(paramName = "onClick", type = ComponentParamType.AjaxHandlerParamType),
    NonAjaxOnSubmitParam(paramName = "onSubmit", type = ComponentParamType.NonAjaxHandlerParamType),
    AjaxOnSubmitParam(paramName = "onSubmit", type = ComponentParamType.AjaxHandlerParamType),
    NonAjaxOnErrorParam(
        paramName = "onError",
        type = ComponentParamType.NonAjaxHandlerParamType,
        defaultNullable = true
    ),
    AjaxOnErrorParam(
        paramName = "onError",
        type = ComponentParamType.AjaxHandlerParamType,
        defaultNullable = true
    ),
    FormValidatorsParam(
        paramName = "validators",
        type = ComponentParamType.ListParamType,
        constraintTypeName = IFormValidator::class.asTypeName()
    ),
    ChoicesParam(paramName = "choices", type = ComponentParamType.ListParamType),
    RendererParam(paramName = "renderer", type = ComponentParamType.RendererParamType)
}

fun ComponentParam.toParamSpec(typeName: TypeName? = null, nullable: Boolean? = null): ParameterSpec {
    var t = type.typeFactory(typeName ?: constraintTypeName)
    t = if (nullable ?: defaultNullable) t.asNullable() else t.asNonNullable()
    val builder = ParameterSpec.builder(paramName, t)
    if (nullable ?: defaultNullable) builder.defaultValue("null")
    return builder.build()
}

enum class ComponentParamLoc {
    Start,
    Middle,
    End
}

class ComponentParamDef(
    val param: ComponentParam,
    val loc: ComponentParamLoc = ComponentParamLoc.End,
    val prop: Boolean = false,
    val constructorParamIndex: Int? = null,
    val superclassConstructorParamIndex: Int? = null,
    val initParamName: String? = null,
    val nullable: Boolean? = null,
    val typeName: TypeName? = null
)

private fun Iterable<ComponentParamDef>.toConstructorFunSpec(): FunSpec {
    val list = this.toList()
    val first = list.filter { it.loc == ComponentParamLoc.Start }.sortedBy { it.constructorParamIndex }
    val middle = list.filter { it.loc == ComponentParamLoc.Middle }.sortedBy { it.constructorParamIndex }
    val end = list.filter { it.loc == ComponentParamLoc.End }.sortedBy { it.constructorParamIndex }
    val defs = first + middle + end
    val params = defs.map {
        it.param.toParamSpec(typeName = it.typeName, nullable = it.nullable)
    }
    val builder = FunSpec.constructorBuilder()
    builder.addParameters(params)
    return builder.build()
}

class ComponentDef(
    val componentClass: KClass<*>,
    val classTypeParam: TypeName? = null,
    val props: List<ComponentParamDef>,
    val initBlock: CodeBlock? = null,
    val funcs: Iterable<FunSpec> = emptyList()
)

val defaultIdParam = ComponentParamDef(
    loc = ComponentParamLoc.Start,
    param = ComponentParam.IdParam,
    constructorParamIndex = 10,
    nullable = false,
    superclassConstructorParamIndex = 10
)

fun defaultModelParam(typeName: TypeName, nullable: Boolean? = null) = ComponentParamDef(
    loc = ComponentParamLoc.Start,
    param = ComponentParam.ModelParam,
    constructorParamIndex = 20,
    nullable = nullable,
    typeName = typeName,
    superclassConstructorParamIndex = 20
)

val defaultOutputMarkupIdParam = ComponentParamDef(
    loc = ComponentParamLoc.Middle,
    param = ComponentParam.OutputMarkupIdParam,
    constructorParamIndex = 10,
    initParamName = "outputMarkupId"
)

val defaultOutputPlaceholderMarkupTagParam = ComponentParamDef(
    loc = ComponentParamLoc.Middle,
    param = ComponentParam.OutputMarkupPlaceholderIdParam,
    constructorParamIndex = 20,
    initParamName = "outputMarkupPlaceholderTag"
)

val defaultVisibleParam = ComponentParamDef(
    loc = ComponentParamLoc.Middle,
    param = ComponentParam.VisibleParam,
    constructorParamIndex = 30,
    initParamName = "visible"
)

val defaultEnabledParam = ComponentParamDef(
    loc = ComponentParamLoc.Middle,
    param = ComponentParam.EnabledParam,
    constructorParamIndex = 40,
    initParamName = "enabled"
)

val defaultEscapeModelStringsParam = ComponentParamDef(
    loc = ComponentParamLoc.Middle,
    param = ComponentParam.EscapeModelStringsParam,
    constructorParamIndex = 50,
    initParamName = "escapeModelStrings"
)

val defaultRenderBodyOnlyParam = ComponentParamDef(
    loc = ComponentParamLoc.Middle,
    param = ComponentParam.RenderBodyOnlyParam,
    constructorParamIndex = 60,
    initParamName = "renderBodyOnly"
)

val defaultBehaviorsParam = ComponentParamDef(
    loc = ComponentParamLoc.Middle,
    param = ComponentParam.BehaviorsParam,
    constructorParamIndex = 70,
    initParamName = "behaviors"
)

val defaultRequiredParam = ComponentParamDef(
    loc = ComponentParamLoc.End,
    param = ComponentParam.RequiredParam,
    constructorParamIndex = 10,
    initParamName = "required"
)

val defaultLabelParam = ComponentParamDef(
    loc = ComponentParamLoc.End,
    param = ComponentParam.LabelParam,
    constructorParamIndex = 20,
    initParamName = "label"
)

val defaultValidatorsParam = ComponentParamDef(
    loc = ComponentParamLoc.End,
    param = ComponentParam.FormValidatorsParam,
    constructorParamIndex = 100
)

fun defaultTypeParam(typeName: TypeName = defaultGenericdModelType, nullable: Boolean? = null) = ComponentParamDef(
    loc = ComponentParamLoc.Start,
    param = ComponentParam.TypeParam,
    constructorParamIndex = 30,
    nullable = nullable,
    typeName = typeName,
    superclassConstructorParamIndex = 30
)

val defaultPageClassParam = ComponentParamDef(
    loc = ComponentParamLoc.Start,
    param = ComponentParam.PageClassParam,
    constructorParamIndex = 20,
    nullable = false,
    superclassConstructorParamIndex = 20
)

fun defaultNonAjaxOnClickHandlerParam(typeName: TypeName) = ComponentParamDef(
    loc = ComponentParamLoc.Middle,
    param = ComponentParam.NonAjaxClickHandlerParam,
    constructorParamIndex = 5,
    nullable = false,
    prop = true,
    typeName = typeName
)

fun defaultAjaxOnClickHandlerParam(typeName: TypeName) = ComponentParamDef(
    loc = ComponentParamLoc.Middle,
    param = ComponentParam.AjaxClickHandlerParam,
    constructorParamIndex = 5,
    nullable = false,
    prop = true,
    typeName = typeName
)

fun defaultNonAjaxOnSubmitHandlerParam(typeName: TypeName) = ComponentParamDef(
    loc = ComponentParamLoc.Middle,
    param = ComponentParam.NonAjaxOnSubmitParam,
    constructorParamIndex = 5,
    nullable = false,
    prop = true,
    typeName = typeName
)

fun defaultNonAjaxOnErrorHandlerParam(typeName: TypeName) = ComponentParamDef(
    loc = ComponentParamLoc.Middle,
    param = ComponentParam.NonAjaxOnErrorParam,
    constructorParamIndex = 6,
    prop = true,
    typeName = typeName
)

fun defaultAjaxOnSubmitHandlerParam(typeName: TypeName) = ComponentParamDef(
    loc = ComponentParamLoc.Middle,
    param = ComponentParam.AjaxOnSubmitParam,
    constructorParamIndex = 5,
    nullable = false,
    prop = true,
    typeName = typeName
)

fun defaultAjaxOnErrorHandlerParam(typeName: TypeName) = ComponentParamDef(
    loc = ComponentParamLoc.Middle,
    param = ComponentParam.AjaxOnErrorParam,
    constructorParamIndex = 6,
    prop = true,
    typeName = typeName
)

fun defaultChoicesParam(typeName: TypeName) = ComponentParamDef(
    loc = ComponentParamLoc.Start,
    param = ComponentParam.ChoicesParam,
    constructorParamIndex = 30,
    superclassConstructorParamIndex = 30,
    nullable = false,
    typeName = typeName
)

fun defaultRendererParam(typeName: TypeName) = ComponentParamDef(
    loc = ComponentParamLoc.Start,
    param = ComponentParam.RendererParam,
    constructorParamIndex = 40,
    superclassConstructorParamIndex = 40,
    nullable = false,
    typeName = typeName
)

val defaultNullValidParam = ComponentParamDef(
    loc = ComponentParamLoc.Middle,
    param = ComponentParam.NullValidParam,
    constructorParamIndex = 5
)

val defaultDefaultFormProcessingParam = ComponentParamDef(
    loc = ComponentParamLoc.Middle,
    param = ComponentParam.DefaultFormProcessingParam,
    constructorParamIndex = 100
)

val defaultFormProcessingCodeBlock =
    CodeBlock.of(
        "\n%L?.let { this.defaultFormProcessing = it }",
        defaultDefaultFormProcessingParam.param.paramName
    )

private const val defaultGenericVarName = "T"
val defaultUntypedModelType = WildcardTypeName.subtypeOf(Any::class)
val defaultGenericdModelType = TypeVariableName(name = defaultGenericVarName)

val defaultTrailingParams = listOf(
    defaultOutputMarkupIdParam,
    defaultOutputPlaceholderMarkupTagParam,
    defaultVisibleParam,
    defaultEnabledParam,
    defaultEscapeModelStringsParam,
    defaultRenderBodyOnlyParam,
    defaultBehaviorsParam
)

fun defaultParams(
    modelType: TypeName = defaultUntypedModelType,
    modelNullable: Boolean = false
) = listOf(defaultIdParam, defaultModelParam(typeName = modelType, nullable = modelNullable)) + defaultTrailingParams


fun defaultFormParams(
    modelType: TypeName = defaultUntypedModelType,
    modelNullable: Boolean = false
) =
    defaultParams(modelType = modelType, modelNullable = modelNullable) +
            defaultRequiredParam +
            defaultLabelParam

fun defaultGenericFormParams(modelNullable: Boolean = false) =
    defaultFormParams(modelType = defaultGenericdModelType, modelNullable = modelNullable)

fun nonAjaxHandlerFun(funName: String, param: ComponentParamDef) = FunSpec.builder(funName)
    .addModifiers(KModifier.OVERRIDE).addCode(
        CodeBlock.of(
            "%L${if (param.nullable ?: param.param.defaultNullable) "?" else ""}.let { it(this) }\n",
            param.param.paramName
        )
    ).build()

fun ajaxHandlerFun(funName: String, param: ComponentParamDef) = FunSpec.builder(funName)
    .addParameter("target", AjaxRequestTarget::class)
    .addModifiers(KModifier.OVERRIDE).addCode(
        CodeBlock.of(
            "%L${if (param.nullable ?: param.param.defaultNullable) "?" else ""}.let { it(target, this) }\n",
            param.param.paramName
        )
    ).build()

private fun createLinkOnClickHandlerParam(ajax: Boolean, componentClass: KClass<*>) =
    if (ajax) defaultAjaxOnClickHandlerParam(
        ParameterizedTypeName.get(
            componentClass.asTypeName(),
            defaultGenericdModelType
        )
    ) else defaultNonAjaxOnClickHandlerParam(
        ParameterizedTypeName.get(
            componentClass.asTypeName(),
            defaultGenericdModelType
        )
    )

private fun createLinkHandlerFun(ajax: Boolean, clickHandler: ComponentParamDef) =
    if (ajax) ajaxHandlerFun(funName = "onClick", param = clickHandler) else nonAjaxHandlerFun(
        funName = "onClick",
        param = clickHandler
    )

fun linkComponentDef(componentClass: KClass<*>, ajax: Boolean): ComponentDef {
    val clickHandler = createLinkOnClickHandlerParam(ajax, componentClass)
    return ComponentDef(
        componentClass = componentClass,
        classTypeParam = genericComponentType,
        props = defaultParams(modelType = defaultGenericdModelType) + clickHandler,
        funcs = listOf(createLinkHandlerFun(ajax, clickHandler))
    )
}

private val myLabel = ComponentDef(
    componentClass = Label::class,
    classTypeParam = defaultUntypedModelType,
    props = defaultParams()
)

private val myGenericPanel = ComponentDef(
    componentClass = GenericPanel::class,
    classTypeParam = defaultGenericdModelType,
    props = defaultParams(modelType = defaultGenericdModelType)
)

private val myTextField = ComponentDef(
    componentClass = TextField::class,
    classTypeParam = defaultGenericdModelType,
    props = defaultFormParams(modelType = defaultGenericdModelType, modelNullable = true)
            + defaultTypeParam(typeName = defaultGenericdModelType, nullable = true)
)

private val myCheckBox = ComponentDef(
    componentClass = CheckBox::class,
    props = defaultFormParams(modelType = Boolean::class.asClassName(), modelNullable = true)
)

private val myTextArea = ComponentDef(
    componentClass = TextArea::class,
    classTypeParam = genericComponentType,
    props = defaultFormParams(modelType = defaultGenericdModelType, modelNullable = true)
)

private val myBookmarkablePageLink = ComponentDef(
    componentClass = BookmarkablePageLink::class,
    classTypeParam = genericComponentType,
    props = listOf(
        defaultIdParam,
        defaultPageClassParam,
        defaultOutputMarkupIdParam,
        defaultOutputPlaceholderMarkupTagParam,
        defaultVisibleParam,
        defaultEnabledParam,
        defaultEscapeModelStringsParam,
        defaultRenderBodyOnlyParam,
        defaultBehaviorsParam
    )
)

private val myLink = ComponentDef(
    componentClass = Link::class,
    classTypeParam = genericComponentType,
    props = defaultParams(modelType = defaultGenericdModelType, modelNullable = true)
            + defaultNonAjaxOnClickHandlerParam(
        ParameterizedTypeName.get(
            Link::class.asTypeName(),
            defaultGenericdModelType
        )
    ),
    funcs = listOf(
        nonAjaxHandlerFun(
            funName = "onClick",
            param = defaultNonAjaxOnClickHandlerParam(genericComponentType)
        )
    )
)

private val myAjaxLink = ComponentDef(
    componentClass = AjaxLink::class,
    classTypeParam = genericComponentType,
    props = defaultParams(modelType = defaultGenericdModelType, modelNullable = true)
            + defaultAjaxOnClickHandlerParam(
        ParameterizedTypeName.get(
            Link::class.asTypeName(),
            defaultGenericdModelType
        )
    ),
    funcs = listOf(
        ajaxHandlerFun(
            funName = "onClick",
            param = defaultAjaxOnClickHandlerParam(genericComponentType)
        )
    )
)

private val myForm = ComponentDef(
    componentClass = Form::class,
    classTypeParam = genericComponentType,
    props = defaultParams(modelType = defaultGenericdModelType, modelNullable = true) + defaultValidatorsParam,
    initBlock = CodeBlock.of("\n%L?.forEach { add(it) }", defaultValidatorsParam.param.paramName)
)

private val myDropDownChoice = ComponentDef(
    componentClass = DropDownChoice::class,
    classTypeParam = genericComponentType,
    props = defaultParams(modelType = defaultGenericdModelType, modelNullable = true)
            + defaultChoicesParam(genericComponentType)
            + defaultRendererParam(genericComponentType)
            + defaultNullValidParam,
    initBlock = CodeBlock.of("\n%L?.let { setNullValid(it) }", defaultNullValidParam.param.paramName)
)

private val myButton = ComponentDef(
    componentClass = Button::class,
    props = defaultParams(modelType = String::class.asTypeName(), modelNullable = true)
            + defaultNonAjaxOnSubmitHandlerParam(Button::class.asTypeName())
            + defaultNonAjaxOnErrorHandlerParam(Button::class.asTypeName())
            + defaultDefaultFormProcessingParam,
    initBlock = CodeBlock.of(
        "\n%L?.let { this.defaultFormProcessing = it }",
        defaultDefaultFormProcessingParam.param.paramName
    ),
    funcs = listOf(
        nonAjaxHandlerFun(funName = "onSubmit", param = defaultNonAjaxOnSubmitHandlerParam(genericComponentType)),
        nonAjaxHandlerFun(funName = "onError", param = defaultNonAjaxOnErrorHandlerParam(genericComponentType))
    )
)

private val myAjaxButton = ComponentDef(
    componentClass = AjaxButton::class,
    props = defaultParams(modelType = String::class.asTypeName(), modelNullable = true)
            + defaultNonAjaxOnSubmitHandlerParam(
        ParameterizedTypeName.get(
            AjaxButton::class.asTypeName(),
            defaultGenericdModelType
        )
    )
            + defaultNonAjaxOnErrorHandlerParam(
        ParameterizedTypeName.get(
            AjaxButton::class.asTypeName(),
            defaultGenericdModelType
        )
    )
            + defaultDefaultFormProcessingParam,
    initBlock = CodeBlock.of(
        "\n%L?.let { this.defaultFormProcessing = it }",
        defaultDefaultFormProcessingParam.param.paramName
    ),
    funcs = listOf(
        ajaxHandlerFun(
            funName = "onSubmit",
            param = defaultAjaxOnClickHandlerParam(genericComponentType)
        ),
        ajaxHandlerFun(
            funName = "onClick",
            param = defaultAjaxOnClickHandlerParam(genericComponentType)
        )
    )
)

private fun List<ComponentParamDef>.createSuperclassConstructorCodeBlock(): CodeBlock {
    val constructorParams =
        filter { it.superclassConstructorParamIndex != null }.sortedBy { it.superclassConstructorParamIndex }
    val superclassConstructorTemplate = constructorParams.joinToString(transform = { "%L" }, separator = ", ")
    val superclassConstructorParamNames = constructorParams.map { it.param.paramName }
    return CodeBlock.of(superclassConstructorTemplate, *superclassConstructorParamNames.toTypedArray())
}

private fun Iterable<ComponentParamDef>.toPropertySpecs() =
    filter { it.prop }.map {
        PropertySpec.builder(
            name = it.param.paramName,
            type = if (it.nullable ?: it.param.defaultNullable)
                it.param.type.typeFactory(it.typeName).asNullable()
            else it.param.type.typeFactory(it.typeName).asNonNullable(),
            modifiers = *arrayOf(KModifier.PRIVATE)
        ).initializer(
            CodeBlock.of("%N", it.param.paramName)
        ).build()
    }

fun ComponentDef.createClass(dir: String, packagePrefix: String) {
    val constructorSpec = props.toConstructorFunSpec()
    val superclassConstr = props.createSuperclassConstructorCodeBlock()
    this.componentClass.java.toComponentWrapperClass(
        packagePrefix = packagePrefix,
        constructorBody = constructorSpec,
        modelType = classTypeParam,
        superclassConstructorParams = superclassConstr,
        initBlock = props.toInitCall() + initBlock,
        props = props.toPropertySpecs(),
        funcs = funcs
    )
        .writeTo(Paths.get(dir))
}

operator fun CodeBlock.plus(other: CodeBlock?) =
    other?.let { CodeBlock.builder().add(this).add(it).build() } ?: this

fun Iterable<ComponentParamDef>.toInitCall(): CodeBlock {
    val initParamNames = filter { it.initParamName != null }
    val body = initParamNames.joinToString(transform = { "%L = %L" }, separator = ",\n")
    val params = initParamNames.flatMap { listOf(it.initParamName, it.param.paramName) }
    return CodeBlock.of("init($body)\n", *params.toTypedArray())
}

fun defaultGenericComponent(
    componentClass: KClass<*>,
    props: List<ComponentParamDef> = defaultParams(modelType = defaultGenericdModelType)
) = ComponentDef(
    componentClass = componentClass,
    classTypeParam = defaultGenericdModelType,
    props = props
)

fun defaultNonGenericComponent(componentClass: KClass<*>, props : List<ComponentParamDef> = defaultParams()) =
    ComponentDef(
        componentClass = componentClass,
        props = props
    )

fun formComponentDef(componentClass: KClass<*>) =
    ComponentDef(
        componentClass = componentClass,
        classTypeParam = genericComponentType,
        props = defaultParams(modelType = defaultGenericdModelType) + defaultValidatorsParam,
        initBlock = CodeBlock.of("\n%L?.forEach { add(it) }", defaultValidatorsParam.param.paramName)
    )

fun generateClasses(dir: String, packagePrefix: String, defs: Iterable<ComponentDef>) {
    val generatedDir = Paths.get(dir)
    Files.mkdirs(File(dir))
    defs.forEach {
        it.createClass(packagePrefix = packagePrefix, dir = dir)
    }
}

fun dropDownComponentDef(componentClass: KClass<*>) =
    ComponentDef(
        componentClass = componentClass,
        classTypeParam = genericComponentType,
        props = defaultParams(modelType = defaultGenericdModelType)
                + defaultChoicesParam(genericComponentType)
                + defaultRendererParam(genericComponentType)
                + defaultNullValidParam,
        initBlock = CodeBlock.of("\n%L?.let { setNullValid(it) }", defaultNullValidParam.param.paramName)
    )

fun buttonComponentDef(componentClass: KClass<*>, ajax: Boolean) =
    if (!ajax)
        ComponentDef(
            componentClass = componentClass,
            props = defaultParams(modelType = String::class.asClassName())
                    + defaultDefaultFormProcessingParam
                    + defaultNonAjaxOnSubmitHandlerParam(Button::class.asTypeName())
                    + defaultNonAjaxOnErrorHandlerParam(Button::class.asTypeName()),
            initBlock = defaultFormProcessingCodeBlock,
            funcs = listOf(
                nonAjaxHandlerFun(
                    funName = "onSubmit",
                    param = defaultNonAjaxOnSubmitHandlerParam(genericComponentType)
                ),
                nonAjaxHandlerFun(
                    funName = "onError",
                    param = defaultNonAjaxOnErrorHandlerParam(genericComponentType)
                )
            )
        )
    else
        ComponentDef(
            componentClass = componentClass,
            props = defaultParams(modelType = String::class.asTypeName())
                    + defaultAjaxOnSubmitHandlerParam(AjaxButton::class.asTypeName())
                    + defaultAjaxOnErrorHandlerParam(AjaxButton::class.asTypeName())
                    + defaultDefaultFormProcessingParam,
            initBlock = defaultFormProcessingCodeBlock,
            funcs = listOf(
                ajaxHandlerFun(
                    funName = "onSubmit",
                    param = defaultAjaxOnSubmitHandlerParam(genericComponentType)
                ),
                ajaxHandlerFun(
                    funName = "onError",
                    param = defaultAjaxOnErrorHandlerParam(genericComponentType)
                )
            )
        )

fun main(vararg args: String) {
    val defs = listOf(
        myLabel, myGenericPanel, myTextField, myCheckBox,
        myTextArea, myBookmarkablePageLink, myLink, myForm, myDropDownChoice, myButton,
        myAjaxLink, myAjaxButton
    )
    defs.forEach {
        it.createClass("c:/temp/andrew", packagePrefix = "org.apache")
    }
}
