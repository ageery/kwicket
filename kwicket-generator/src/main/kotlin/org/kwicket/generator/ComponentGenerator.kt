@file:JvmName("ComponentGenerator")
package org.kwicket.generator

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.ParameterizedTypeName
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.WildcardTypeName
import com.squareup.kotlinpoet.asTypeName
import org.apache.wicket.behavior.Behavior
import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.markup.html.basic.MultiLineLabel
import org.apache.wicket.markup.html.panel.GenericPanel
import org.apache.wicket.markup.html.panel.Panel
import org.apache.wicket.model.IModel
import java.nio.file.Paths
import kotlin.reflect.KClass

private val basePackageNames = listOf("org.apache")

private fun Package.toNewPackageName() =
    basePackageNames.fold(name) { acc, next -> acc.replaceFirst("$next", "org.kwicket") }

private fun Class<*>.toKwicketClassname() = "K$simpleName"
private fun KClass<*>.toKwicketClassname() = "K$simpleName"

private const val constructorIdParamName = "id"
private const val constructorModelParamName = "model"
private const val constructorOutputMarkupIdParamName = "outputMarkupId"
private const val constructorOutputMarkupPlaceholderTagParamName = "outputMarkupPlaceholderTag"
private const val constructorVisibleParamName = "visible"
private const val constructorEnabledParamName = "enabled"
private const val constructorEscapeModelStringsParamName = "escapeModelStrings"
private const val constructorRenderBodyOnlyParamName = "renderBodyOnly"
private const val constructorBehaviorsParamName = "behaviors"

private const val initOutputMarkupIdParamName = constructorOutputMarkupIdParamName
private const val initOutputMarkupPlaceholderTagParamName = constructorOutputMarkupPlaceholderTagParamName
private const val initVisibleParamName = constructorVisibleParamName
private const val initEnabledParamName = constructorEnabledParamName
private const val initEscapeModelStringsParamName = constructorEscapeModelStringsParamName
private const val initRenderBodyOnlyParamName = constructorRenderBodyOnlyParamName
private const val initBehaviorsParamName = constructorBehaviorsParamName

private fun FunSpec.Builder.nullableBoolean(paramName: String, defaultValue: String = "null") = addParameter(
    ParameterSpec.builder(paramName, Boolean::class.asTypeName().asNullable())
        .defaultValue(defaultValue)
        .build()
)

private fun FunSpec.Builder.addIdParam() = addParameter(constructorIdParamName, String::class)
private fun FunSpec.Builder.addGenericModelParam() = addParameter(
    constructorModelParamName, ParameterizedTypeName.get(
        IModel::class.asTypeName(),
        WildcardTypeName.subtypeOf(Any::class)
    )
)

private fun FunSpec.Builder.addOutputMarkupIdParam() = nullableBoolean(constructorOutputMarkupIdParamName)
private fun FunSpec.Builder.addOutputMarkupPlaceholderTagParam() =
    nullableBoolean(constructorOutputMarkupPlaceholderTagParamName)

private fun FunSpec.Builder.addVisibleParam() = nullableBoolean(constructorVisibleParamName)
private fun FunSpec.Builder.addEnabledParam() = nullableBoolean(constructorEnabledParamName)
private fun FunSpec.Builder.addEscapeModelStringsParam() = nullableBoolean(constructorEscapeModelStringsParamName)
private fun FunSpec.Builder.addRenderBodyOnlyParam() = nullableBoolean(constructorRenderBodyOnlyParamName)
private fun FunSpec.Builder.addBehaviorsParam() =
    addParameter(constructorBehaviorsParamName, Behavior::class, KModifier.VARARG)

private fun CodeBlock.Builder.toSimpleInitCall() =
    addStatement(
        """init(%L = %L,
            |%L = %L,
            |%L = %L,
            |%L = %L,
            |%L = %L,
            |%L = %L,
            |%L = *arrayOf(%L))"""
            .trimMargin(),
        initOutputMarkupIdParamName, constructorOutputMarkupIdParamName,
        initOutputMarkupPlaceholderTagParamName, constructorOutputMarkupPlaceholderTagParamName,
        initVisibleParamName, constructorVisibleParamName,
        initEnabledParamName, constructorEnabledParamName,
        initEscapeModelStringsParamName, constructorEscapeModelStringsParamName,
        initRenderBodyOnlyParamName, constructorRenderBodyOnlyParamName,
        initBehaviorsParamName, constructorBehaviorsParamName
    )

fun Class<*>.toSimpleWrapperClass(): FileSpec =
    FileSpec.builder(`package`.toNewPackageName(), toKwicketClassname())
        .addType(
            TypeSpec.classBuilder(toKwicketClassname())
                .addModifiers(KModifier.OPEN)
                .superclass(this)
                .addSuperclassConstructorParameter("%L, %L", constructorIdParamName, constructorModelParamName)
                .primaryConstructor(
                    FunSpec.constructorBuilder()
                        .addIdParam()
                        .addGenericModelParam()
                        .addOutputMarkupIdParam()
                        .addOutputMarkupPlaceholderTagParam()
                        .addVisibleParam()
                        .addEnabledParam()
                        .addEscapeModelStringsParam()
                        .addRenderBodyOnlyParam()
                        .addBehaviorsParam()
                        .build()
                )
                .addInitializerBlock(
                    CodeBlock.builder()
                        .toSimpleInitCall()
                        .build()
                )
                .build()
        )
        .build()

fun KClass<*>.toSimpleWrapperClass(): FileSpec = java.toSimpleWrapperClass()
//    FileSpec.builder(java.`package`.toNewPackageName(), toKwicketClassname())
//        .addType(
//            TypeSpec.classBuilder(toKwicketClassname())
//                .addModifiers(KModifier.OPEN)
//                .superclass(this)
//                .addSuperclassConstructorParameter("%L, %L", constructorIdParamName, constructorModelParamName)
//                .primaryConstructor(
//                    FunSpec.constructorBuilder()
//                        .addIdParam()
//                        .addGenericModelParam()
//                        .addOutputMarkupIdParam()
//                        .addOutputMarkupPlaceholderTagParam()
//                        .addVisibleParam()
//                        .addEnabledParam()
//                        .addEscapeModelStringsParam()
//                        .addRenderBodyOnlyParam()
//                        .addBehaviorsParam()
//                        .build()
//                )
//                .addInitializerBlock(
//                    CodeBlock.builder()
//                        .toSimpleInitCall()
//                        .build()
//                )
//                .build()
//        )
//        .build()

fun f() {
    val classes = listOf(
        Label::class,
        MultiLineLabel::class,
        Panel::class,
        GenericPanel::class
    )
    classes.map { it.toSimpleWrapperClass() }
        .forEach {
            it.writeTo(Paths.get("c:\\temp\\kwicket-gen"))
        }
}

fun main(args: Array<String>) {
    val classes = listOf(
        Label::class,
        MultiLineLabel::class,
        Panel::class,
        GenericPanel::class
    )
    classes.map { it.toSimpleWrapperClass() }
        .forEach {
            it.writeTo(Paths.get("c:\\temp\\kwicket-gen"))
        }
//        .map {
//            val sb = StringBuilder()
//            it.writeTo(sb)
//            sb.toString()
//        }.forEach { println(it) }
}