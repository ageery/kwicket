@file:JvmName("ComponentGenerator")

package org.kwicket.generator

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.LambdaTypeName
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.ParameterizedTypeName
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.TypeVariableName
import com.squareup.kotlinpoet.WildcardTypeName
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.asTypeName
import org.apache.wicket.Page
import org.apache.wicket.behavior.Behavior
import org.apache.wicket.markup.html.form.Button
import org.apache.wicket.markup.html.form.CheckBox
import org.apache.wicket.markup.html.form.DropDownChoice
import org.apache.wicket.markup.html.form.Form
import org.apache.wicket.markup.html.form.IChoiceRenderer
import org.apache.wicket.markup.html.form.TextArea
import org.apache.wicket.markup.html.form.TextField
import org.apache.wicket.markup.html.link.BookmarkablePageLink
import org.apache.wicket.markup.html.link.Link
import org.apache.wicket.markup.html.panel.GenericPanel
import org.apache.wicket.model.IModel
import org.apache.wicket.validation.IValidator
import kotlin.reflect.KClass

//private val basePackageNames = listOf("org.apache")
//
//private fun Package.toNewPackageName() =
//    basePackageNames.fold(paramName) { acc, next -> acc.replaceFirst("$next", "org.kwicket") }



private fun Class<*>.toKwicketClassname() = "K$simpleName"
//private fun KClass<*>.toKwicketClassname() = "K$simpleName"

private const val constructorIdParamName = "id"
private const val constructorModelParamName = "model"
private const val constructorOutputMarkupIdParamName = "outputMarkupId"
private const val constructorOutputMarkupPlaceholderTagParamName = "outputMarkupPlaceholderTag"
private const val constructorVisibleParamName = "visible"
private const val constructorEnabledParamName = "enabled"
private const val constructorEscapeModelStringsParamName = "escapeModelStrings"
private const val constructorRenderBodyOnlyParamName = "renderBodyOnly"
private const val constructorBehaviorsParamName = "behaviors"
private const val constructorTypeParamName = "typeFactory"
private const val constructorRequiredParamName = "required"
private const val constructorLabelParamName = "label"
private const val constructorValidatorsParamName = "validators"
private const val constructorPageClassParamName = "pageClass"
private const val constructorNullValidParamName = "nullValid"
private const val constructorChoicesParamName = "choices"
private const val constructorRendererParamName = "renderer"

private const val initOutputMarkupIdParamName = constructorOutputMarkupIdParamName
private const val initOutputMarkupPlaceholderTagParamName = constructorOutputMarkupPlaceholderTagParamName
private const val initVisibleParamName = constructorVisibleParamName
private const val initEnabledParamName = constructorEnabledParamName
private const val initEscapeModelStringsParamName = constructorEscapeModelStringsParamName
private const val initRenderBodyOnlyParamName = constructorRenderBodyOnlyParamName
private const val initBehaviorsParamName = constructorBehaviorsParamName
private const val initRequiredParamName = constructorRequiredParamName
private const val initLabelParamName = constructorLabelParamName

val constructorDefaultFormProcessingParamName = "defaultFormProcessing"
val constructorOnSubmitParamName = "onSubmit"
val constructorOnClickParamName = "onClick"
val constructorOnErrorParamName = "onError"
val nullDefaultValue = "null"

val genericComponentType = TypeVariableName(name = "T")

fun FunSpec.Builder.addNullableBooleanParameter(paramName: String, defaultValue: String = nullDefaultValue) =
    addParameter(
        ParameterSpec.builder(paramName, Boolean::class.asTypeName().asNullable())
            .defaultValue(defaultValue)
            .build()
    )

fun FunSpec.Builder.addDefaultFormProcessingParameter() =
    addNullableBooleanParameter(constructorDefaultFormProcessingParamName)

val nullableNonAjaxHandler = LambdaTypeName.get(returnType = Unit::class.asClassName()).asNullable()

fun FunSpec.Builder.addNonAjaxHandlerParameter(name: String) =
    addParameter(
        ParameterSpec.builder(name, nullableNonAjaxHandler)
            .nullDefaultValue()
            .build()
    )

/*private */ fun FunSpec.Builder.addIdParam() = addParameter(constructorIdParamName, String::class)

// FIXME: this should support a subclass!!!
private fun FunSpec.Builder.addModelParam(modelType: TypeName?) = addParameter(
    ParameterSpec.builder(
        constructorModelParamName,
        ParameterizedTypeName.get(
            IModel::class.asTypeName(),
            modelType ?: WildcardTypeName.subtypeOf(Any::class)
        ).asNullable()
    )
        .nullDefaultValue()
        .build()
)

private fun FunSpec.Builder.addPageClassParam() = addParameter(
    ParameterSpec.builder(
        constructorPageClassParamName,
        ParameterizedTypeName.get(
            Class::class.asTypeName(),
            WildcardTypeName.subtypeOf(Page::class)
        )
    ).build()
)

// FIXME: what if we hid these behind an enum?
private fun FunSpec.Builder.addChoicesParam() = addParameter(
    ParameterSpec.builder(
        constructorChoicesParamName,
        ParameterizedTypeName.get(
            List::class.asTypeName(),
            WildcardTypeName.subtypeOf(genericComponentType)
        )
    ).build()
)

private fun FunSpec.Builder.addRendererParam() = addParameter(
    ParameterSpec.builder(
        constructorRendererParamName,
        ParameterizedTypeName.get(IChoiceRenderer::class.asTypeName(), WildcardTypeName.subtypeOf(genericComponentType))
    ).build()
)

private fun FunSpec.Builder.addLabelParam() =
    addParameter(
        ParameterSpec.builder(
            constructorLabelParamName, ParameterizedTypeName.get(IModel::class.asTypeName(), String::class.asTypeName())
                .asNullable()
        )
            .nullDefaultValue()
            .build()
    )

private fun FunSpec.Builder.addValidatorsParam() =
    addParameter(
        ParameterSpec.builder(
            constructorValidatorsParamName,
            ParameterizedTypeName.get(List::class.asTypeName(), IValidator::class.asTypeName())
                .asNullable()
        )
            .nullDefaultValue()
            .build()
    )

private fun FunSpec.Builder.addTypeParam(typeClass: TypeName) =
    addParameter(
        ParameterSpec.builder(
            constructorTypeParamName, ParameterizedTypeName.get(Class::class.asTypeName(), typeClass)
                .asNullable()
        )
            .nullDefaultValue()
            .build()
    )

private fun FunSpec.Builder.addOutputMarkupIdParam() = addNullableBooleanParameter(constructorOutputMarkupIdParamName)
private fun FunSpec.Builder.addOutputMarkupPlaceholderTagParam() =
    addNullableBooleanParameter(constructorOutputMarkupPlaceholderTagParamName)

private fun FunSpec.Builder.addVisibleParam() = addNullableBooleanParameter(constructorVisibleParamName)
private fun FunSpec.Builder.addEnabledParam() = addNullableBooleanParameter(constructorEnabledParamName)
private fun FunSpec.Builder.addEscapeModelStringsParam() =
    addNullableBooleanParameter(constructorEscapeModelStringsParamName)

private fun FunSpec.Builder.addRenderBodyOnlyParam() = addNullableBooleanParameter(constructorRenderBodyOnlyParamName)
// FIXME: if there is no parameter, we can turn these into vals...
private fun FunSpec.Builder.addBehaviorsParam() =
    addParameter(
        ParameterSpec.builder(
            constructorBehaviorsParamName, ParameterizedTypeName.get(List::class.asTypeName(), Behavior::class.asTypeName())
                .asNullable()
        )
            .nullDefaultValue()
            .build()
    )

private fun FunSpec.Builder.addRequiredParam() = addNullableBooleanParameter(constructorRequiredParamName)
private fun FunSpec.Builder.addNullValidParam() = addNullableBooleanParameter(constructorNullValidParamName)

private fun CodeBlock.Builder.toComponentInitCall() =
    addStatement(
        """init(%L = %L,
            |%L = %L,
            |%L = %L,
            |%L = %L,
            |%L = %L,
            |%L = %L,
            |%L = %L)"""
            .trimMargin(),
        initOutputMarkupIdParamName, constructorOutputMarkupIdParamName,
        initOutputMarkupPlaceholderTagParamName, constructorOutputMarkupPlaceholderTagParamName,
        initVisibleParamName, constructorVisibleParamName,
        initEnabledParamName, constructorEnabledParamName,
        initEscapeModelStringsParamName, constructorEscapeModelStringsParamName,
        initRenderBodyOnlyParamName, constructorRenderBodyOnlyParamName,
        initBehaviorsParamName, constructorBehaviorsParamName
    )

private fun TypeSpec.Builder.superclass(c: Class<*>, modelType: TypeName?) =
    if (modelType == null) superclass(c) else superclass(ParameterizedTypeName.get(c.asClassName(), modelType))

private fun createConstructor(
    modelType: TypeName?,
    primaryParamLambda: ((FunSpec.Builder) -> Unit),
    postPrimaryParamLambda: ((FunSpec.Builder).() -> Unit)?,
    paramLambda: ((FunSpec.Builder) -> Unit)?
): FunSpec {
    val builder = FunSpec.constructorBuilder()
    builder
        .addIdParam()
    primaryParamLambda(builder)
    postPrimaryParamLambda?.let { it(builder) }
    builder
        .addOutputMarkupIdParam()
        .addOutputMarkupPlaceholderTagParam()
        .addVisibleParam()
        .addEnabledParam()
        .addEscapeModelStringsParam()
        .addRenderBodyOnlyParam()
        .addBehaviorsParam()
    paramLambda?.let { it(builder) }
    return builder.build()
}

private fun createInitBlock(initBlockLambda: ((CodeBlock.Builder) -> Unit)?): CodeBlock {
    val builder = CodeBlock.builder()
    builder.toComponentInitCall()
    initBlockLambda?.let { it(builder) }
    return builder.build()
}
//
//class ComponentDef<C : Component>(
//    val componentClass: KClass<C>,
//    val classTypeParam: TypeName? = null
//)
//
//fun ComponentDef<*>.toWrapperClass(packagePrefix: String, dir: String) {
//    componentClass.java.toComponentWrapperClass(
//        packagePrefix = packagePrefix,
//        classTypeParam = classTypeParam
//    ).writeTo(Paths.get(dir))
//}

fun Class<*>.toComponentWrapperClass(
    packagePrefix: String,
    modelType: TypeName? = null,
    constructorBody: FunSpec? = null,
    superclassGenericType: TypeName? = modelType as? TypeVariableName,
    superclassConstructorParams: CodeBlock = CodeBlock.of("%L, %L", constructorIdParamName, constructorModelParamName),
    primaryParamLambda: ((FunSpec.Builder).() -> Unit) = { addModelParam(modelType) },
    postPrimaryParamLambda: ((FunSpec.Builder).() -> Unit)? = null,
    paramLambda: ((FunSpec.Builder).() -> Unit)? = null,
    props: Iterable<PropertySpec> = emptyList(),
    initBlockLambda: ((CodeBlock.Builder).() -> Unit)? = null,
    initBlock: CodeBlock? = null,
    funcs: Iterable<FunSpec> = emptyList()
): FileSpec =
    FileSpec.builder(`package`.name.replaceFirst(packagePrefix, "org.kwicket.andrew"), toKwicketClassname())
        .addType(
            TypeSpec.classBuilder(toKwicketClassname())
                .addModifiers(KModifier.OPEN)
                .superclass(this, superclassGenericType)
                .addSuperclassConstructorParameter(superclassConstructorParams)
                .primaryConstructor(
                    constructorBody ?:
                    createConstructor(
                        modelType,
                        primaryParamLambda,
                        postPrimaryParamLambda,
                        paramLambda
                    )
                )
                .addInitializerBlock(initBlock ?: createInitBlock(initBlockLambda))
                .addProperties(props)
                .addTypeVariables(if (superclassGenericType is TypeVariableName) listOf(superclassGenericType) else emptyList())
                .addFunctions(funcs)
                .build()
        )
        .addStaticImport("org.kwicket.component", "init")
        .addStaticImport("org.kwicket.model", "value")

        .build()

//fun KClass<*>.toComponentWrapperClass(packagePrefix: String): FileSpec =
//    java.toComponentWrapperClass(packagePrefix = packagePrefix)

//fun generateClases(packagePrefix: String, genDir: String, vararg classes: KClass<*>) {
//    classes.forEach { c ->
//        println("------$c")
//        c.toComponentWrapperClass(packagePrefix = packagePrefix).writeTo(Paths.get(genDir))
//    }
//}

sealed class WicketComponent(val cl: KClass<*>, val modelType: TypeVariableName? = null)

class BasicWicketComponent(cl: KClass<*>) : WicketComponent(cl = cl)

class GenericWicketComponent(cl: KClass<*>) : WicketComponent(cl = cl, modelType = TypeVariableName(name = "T"))

// FIXME: here is the entry point into this code
//fun generateClasses(packagePrefix: String, genDir: String, vararg componentDefs: WicketComponent) {
//    val outputDir = Paths.get(genDir)
//    componentDefs.forEach { def ->
//        def.cl::class.java.toComponentWrapperClass(packagePrefix = packagePrefix, classTypeParam = def.classTypeParam)
//            .writeTo(outputDir)
//    }
//}

//fun f() {
//    val classes = listOf(
//        Label::class,
//        MultiLineLabel::class,
//        Panel::class,
//        GenericPanel::class
//    )
//    classes.map { it.toComponentWrapperClass(packagePrefix = "org.apache") }
//        .forEach {
//            it.writeTo(Paths.get("c:\\temp\\kwicket-gen"))
//        }
//}

fun ParameterSpec.Builder.nullDefaultValue() = defaultValue(nullDefaultValue)

private fun paramPropSpec(name: String, type: TypeName, vararg modifiers: KModifier = arrayOf(KModifier.PRIVATE)) =
    PropertySpec.builder(
        name = name,
        type = type,
        modifiers = *modifiers
    ).initializer(
        CodeBlock.of("%N", name)
    ).build()

private fun CodeBlock.Builder.formComponentInit() = addStatement(
    """init(label = %L,
            |required = %L)""".trimMargin(), constructorLabelParamName, constructorRequiredParamName
)

/*
 * param locs: after regular ones, after id, after model
 */

enum class ComponentParams(val param: FunSpec.Builder.() -> Unit) {
    Id(param = { addParameter(org.kwicket.generator.constructorIdParamName, kotlin.String::class) })
}

// FIXME: do we want to accept a list of package-qualified class names on the command-line?
fun main(args: Array<String>) {

    val sb = StringBuilder()
    //Label::class.toComponentWrapperClass(packagePrefix = "org.apache").writeTo(sb)
    GenericPanel::class.java.toComponentWrapperClass(
        packagePrefix = "org.apache",
        modelType = genericComponentType
    ).writeTo(sb)
    Button::class.java.toComponentWrapperClass(
        packagePrefix = "org.apache",
        paramLambda = {
            addDefaultFormProcessingParameter()
            addNonAjaxHandlerParameter(constructorOnSubmitParamName)
            addNonAjaxHandlerParameter(constructorOnErrorParamName)
        },
        props = listOf(
            paramPropSpec(name = constructorOnSubmitParamName, type = nullableNonAjaxHandler),
            paramPropSpec(name = constructorOnErrorParamName, type = nullableNonAjaxHandler)
        ),
        initBlockLambda = { addStatement("$constructorDefaultFormProcessingParamName?.let { this.defaultFormProcessing = it }") },
        funcs = listOf(
            FunSpec.builder("onSubmit").addModifiers(KModifier.OVERRIDE).addCode(
                CodeBlock.of(
                    "%L?.let { it() }\n",
                    constructorOnSubmitParamName
                )
            ).build(),
            FunSpec.builder("onError").addModifiers(KModifier.OVERRIDE).addCode(
                CodeBlock.of(
                    "%L?.let { it() }\n",
                    constructorOnErrorParamName
                )
            ).build()
        )
    ).writeTo(sb)

    TextField::class.java.toComponentWrapperClass(
        packagePrefix = "org.apache",
        modelType = genericComponentType,
        superclassConstructorParams = CodeBlock.of(
            "%L, %L, %L",
            constructorIdParamName,
            constructorModelParamName,
            constructorTypeParamName
        ),
        paramLambda = {
            addLabelParam()
            addTypeParam(genericComponentType)
            addRequiredParam()
        },
        initBlockLambda = {
            addStatement(
                """init(label = %L,
            |required = %L)""".trimMargin(), constructorLabelParamName, constructorRequiredParamName
            )
        }
    ).writeTo(sb)

    CheckBox::class.java.toComponentWrapperClass(
        packagePrefix = "org.apache",
        modelType = Boolean::class.asClassName(),
        paramLambda = {
            addLabelParam()
            addRequiredParam()
        },
        initBlockLambda = {
            formComponentInit()
        }
    ).writeTo(sb)

    Form::class.java.toComponentWrapperClass(
        packagePrefix = "org.apache",
        modelType = genericComponentType,
        paramLambda = {
            addValidatorsParam()
        },
        initBlockLambda = {
            addStatement("%L?.forEach { add(it) }", "validators")
        }
    ).writeTo(sb)

    TextArea::class.java.toComponentWrapperClass(
        packagePrefix = "org.apache",
        modelType = genericComponentType
    ).writeTo(sb)

    Link::class.java.toComponentWrapperClass(
        packagePrefix = "org.apache",
        modelType = genericComponentType,
        paramLambda = {
            addNonAjaxHandlerParameter(constructorOnClickParamName)
        },
        funcs = listOf(
            FunSpec.builder("onClick").addModifiers(KModifier.OVERRIDE).addCode(
                CodeBlock.of(
                    "%L?.let { it() }\n",
                    constructorOnClickParamName
                )
            ).build()
        ),
        props = listOf(
            paramPropSpec(name = constructorOnClickParamName, type = nullableNonAjaxHandler)
        )
    ).writeTo(sb)

    BookmarkablePageLink::class.java.toComponentWrapperClass(
        packagePrefix = "org.apache",
        superclassGenericType = genericComponentType,
        primaryParamLambda = {
            addPageClassParam()
        },
        superclassConstructorParams = CodeBlock.of("%L, %L", constructorIdParamName, constructorPageClassParamName)
    ).writeTo(sb)

    DropDownChoice::class.java.toComponentWrapperClass(
        packagePrefix = "org.apache",
        modelType = genericComponentType,
        postPrimaryParamLambda = {
            addChoicesParam()
            addRendererParam()
            addNullValidParam()
        },
        superclassConstructorParams = CodeBlock.of(
            "%L, %L, %L, %L",
            constructorIdParamName, constructorPageClassParamName, constructorChoicesParamName,
            constructorRendererParamName
        ),
        initBlockLambda = {
            addStatement("%L?.let { setNullValid(it) }", constructorNullValidParamName)
        }
    ).writeTo(sb)

    // FIXME: should the onClick, onSuccess and onError also pass the component itself?
    // FIXME: this would allow the lambda to get the model value and/or manipulate the component itself

    println(sb)

//    val classes = listOf(
//        Label::class,
//        MultiLineLabel::class,
//        Panel::class,
//        GenericPanel::class,
//        WebMarkupContainer::class
//    )
//    classes.map { it.toComponentWrapperClass(packagePrefix = "org.apache") }
////        .forEach {
////            it.writeTo(Paths.get("c:\\temp\\kwicket-gen"))
////        }
//        .map {
//            val sb = StringBuilder()
//            it.writeTo(sb)
//            sb.toString()
//        }.forEach { println(it) }
}