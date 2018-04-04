import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.ParameterizedTypeName
import com.squareup.kotlinpoet.TypeVariableName
import com.squareup.kotlinpoet.WildcardTypeName
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.asTypeName
import org.apache.tools.ant.taskdefs.Componentdef
import org.apache.wicket.ajax.markup.html.AjaxLink
import org.apache.wicket.ajax.markup.html.form.AjaxButton
import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.markup.html.form.Button
import org.apache.wicket.markup.html.form.CheckBox
import org.apache.wicket.markup.html.form.CheckGroup
import org.apache.wicket.markup.html.form.DropDownChoice
import org.apache.wicket.markup.html.form.Form
import org.apache.wicket.markup.html.form.HiddenField
import org.apache.wicket.markup.html.form.TextArea
import org.apache.wicket.markup.html.form.TextField
import org.apache.wicket.markup.html.link.BookmarkablePageLink
import org.apache.wicket.markup.html.link.Link
import org.apache.wicket.markup.html.panel.GenericPanel
import org.apache.wicket.markup.html.panel.Panel
import org.gradle.api.internal.HasConvention
import org.gradle.kotlin.dsl.provider.kotlinScriptClassPathProviderOf
import org.jetbrains.kotlin.gradle.dsl.Coroutines
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.kwicket.generator.toComponentWrapperClass
import org.kwicket.generator.genericComponentType
import org.kwicket.generator.ComponentDef
import org.kwicket.generator.createClass
import org.kwicket.generator.defaultBehaviorsParam
import org.kwicket.generator.defaultChoicesParam
import org.kwicket.generator.defaultDefaultFormProcessingParam
import org.kwicket.generator.defaultEnabledParam
import org.kwicket.generator.defaultEscapeModelStringsParam
import org.kwicket.generator.defaultFormParams
import org.kwicket.generator.defaultGenericdModelType
import org.kwicket.generator.defaultIdParam
import org.kwicket.generator.defaultNonAjaxOnClickHandlerParam
import org.kwicket.generator.defaultNonAjaxOnErrorHandlerParam
import org.kwicket.generator.defaultNullValidParam
import org.kwicket.generator.defaultOutputMarkupIdParam
import org.kwicket.generator.defaultPageClassParam
import org.kwicket.generator.defaultUntypedModelType
import org.kwicket.generator.defaultOutputPlaceholderMarkupTagParam
import org.kwicket.generator.defaultNonAjaxOnSubmitHandlerParam
import org.kwicket.generator.defaultNonAjaxOnErrorHandlerParam
import org.kwicket.generator.defaultNonAjaxOnSubmitHandlerParam
import org.kwicket.generator.defaultTrailingParams
import org.kwicket.generator.defaultParams
import org.kwicket.generator.defaultRenderBodyOnlyParam
import org.kwicket.generator.defaultRendererParam
import org.kwicket.generator.defaultTypeParam
import org.kwicket.generator.defaultValidatorsParam
import org.kwicket.generator.defaultVisibleParam
import org.kwicket.generator.ajaxHandlerFun
import org.kwicket.generator.main
import org.kwicket.generator.nonAjaxHandlerFun
import org.kwicket.generator.defaultAjaxOnClickHandlerParam
import org.kwicket.generator.defaultAjaxOnSubmitHandlerParam
import org.kwicket.generator.defaultAjaxOnErrorHandlerParam
import org.kwicket.generator.defaultFormProcessingCodeBlock
import org.kwicket.generator.linkComponentDef
import java.nio.file.Paths
import org.kwicket.generator.generateClasses
import org.kwicket.generator.defaultGenericComponent
import org.kwicket.generator.defaultGenericFormParams
import org.kwicket.generator.formComponentDef
import org.kwicket.generator.buttonComponentDef
import org.kwicket.generator.dropDownComponentDef
import org.kwicket.generator.defaultNonGenericComponent

buildscript {

    val kotlinVersion = "1.2.31"
    val dokkaVersion = "0.9.16"
    val bintrayVersion = "1.7.3"
    val artifactoryVersion = "4.5.2"

    repositories {
        mavenCentral()
        jcenter()
        mavenLocal()
    }

    dependencies {
        //project(":kwicket-generator")
        classpath("org.apache.wicket:wicket-core:8.0.0-M9")
    }

}

//////////////////////////////////////////
val sourceSets = java.sourceSets
fun sourceSets(block: SourceSetContainer.() -> Unit) = sourceSets.apply(block)

val SourceSetContainer.main: SourceSet get() = getByName("main")
val SourceSetContainer.test: SourceSet get() = getByName("test")
fun SourceSetContainer.main(block: SourceSet.() -> Unit) = main.apply(block)
fun SourceSetContainer.test(block: SourceSet.() -> Unit) = test.apply(block)

val SourceSet.kotlin: SourceDirectorySet
    get() = (this as HasConvention).convention.getPlugin<KotlinSourceSet>().kotlin
var SourceDirectorySet.sourceDirs: Iterable<File>
    get() = srcDirs
    set(value) {
        setSrcDirs(value)
    }

// FIXME: move this into the parent
sourceSets {
    main {
        kotlin.sourceDirs += files("$buildDir/generated")
    }
}

task("codeGeneration") {
    doLast {
        generateClasses(
            dir = "$buildDir/generated", packagePrefix = "org.apache", defs = listOf(
                defaultNonGenericComponent(componentClass = Label::class),
                defaultNonGenericComponent(componentClass = Panel::class),
                // FIXME: modify the modelType to be a lambda where the type var is passed in
                defaultGenericComponent(
                    componentClass = CheckGroup::class,
                    props = defaultParams(
                        modelType = WildcardTypeName.subtypeOf(
                            ParameterizedTypeName.get(
                                Collection::class.asTypeName(),
                                TypeVariableName(name = "T")
                            )
                        )
                    )
                ),
                // FIXME: add Check
                defaultGenericComponent(componentClass = GenericPanel::class),
                defaultGenericComponent(componentClass = HiddenField::class),
                defaultGenericComponent(
                    componentClass = TextField::class,
                    props = defaultGenericFormParams() + defaultTypeParam()
                ),
                ComponentDef(
                    componentClass = CheckBox::class,
                    props = defaultFormParams(modelType = Boolean::class.asClassName())
                ),
                defaultGenericComponent(componentClass = TextArea::class),
                defaultGenericComponent(
                    componentClass = BookmarkablePageLink::class,
                    props = listOf(defaultIdParam, defaultPageClassParam) + defaultTrailingParams
                ),
                formComponentDef(componentClass = Form::class),
                dropDownComponentDef(componentClass = DropDownChoice::class),
                linkComponentDef(componentClass = Link::class, ajax = false),
                linkComponentDef(componentClass = AjaxLink::class, ajax = true),
                buttonComponentDef(componentClass = Button::class, ajax = false),
                buttonComponentDef(componentClass = AjaxButton::class, ajax = true)
            )
        )
    }
}


// FIXME: move this into the parent
tasks {
    withType<KotlinCompile> {
        dependsOn("codeGeneration")
        kotlin {
            experimental.coroutines = Coroutines.ENABLE
        }
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs = listOf("-Xjsr305=strict")
        }
    }
}


dependencies {
    compile(project(":kwicket-core"))
    compileOnly("javax.servlet:javax.servlet-api:3.1.0")
    compileOnly("org.apache.wicket:wicket-core:8.0.0-M9")
}
