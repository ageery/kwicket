import com.jfrog.bintray.gradle.BintrayExtension
import com.jfrog.bintray.gradle.BintrayPlugin
import org.gradle.jvm.tasks.Jar
import org.jfrog.gradle.plugin.artifactory.task.ArtifactoryTask

import org.kwicket.gradle.KwicketConfig.kotlinxHtmlVersion
import org.kwicket.gradle.KwicketConfig.wicketBootstrapVersion
import org.kwicket.gradle.KwicketConfig.wicketStuffVersion
import org.kwicket.gradle.KwicketConfig.wicketVersion

plugins {
    val dependencyManagementVersion = org.kwicket.gradle.KwicketConfig.dependencyManagementVersion
    val kotlinVersion = org.kwicket.gradle.KwicketConfig.kotlinVersion
    val bootVersion = org.kwicket.gradle.KwicketConfig.bootVersion
    id("org.jetbrains.kotlin.jvm")
    id("io.spring.dependency-management") version dependencyManagementVersion
    id("org.jetbrains.kotlin.plugin.spring") version kotlinVersion
    id("org.springframework.boot") version bootVersion
}

dependencies {

    compile("org.springframework.boot:spring-boot-starter-web")
    compile("org.springframework.boot:spring-boot-starter-tomcat")
    testCompile("org.springframework.boot:spring-boot-starter-test")
    compileOnly("javax.servlet:javax.servlet-api:3.1.0")

    compile(project(":kwicket-core"))

    compile("org.jetbrains.kotlinx:kotlinx-html-jvm:$kotlinxHtmlVersion")
    compile(project(":kwicket-kotlinx-html"))

    compile("org.apache.wicket:wicket-core:$wicketVersion")
    compile(project(":kwicket-wicket-core"))

    compile("org.apache.wicket:wicket-extensions:$wicketVersion")
    compile(project(":kwicket-wicket-extensions"))

    compile("org.apache.wicket:wicket-spring:$wicketVersion")
    compile(project(":kwicket-wicket-spring"))

    compile("org.wicketstuff:wicketstuff-annotation:$wicketStuffVersion")
    compile(project(":kwicket-wicketstuff-annotation"))

    compile("org.wicketstuff:wicketstuff-select2:$wicketStuffVersion")
    compile(project(":kwicket-wicketstuff-select2"))

    compile("de.agilecoders.wicket:wicket-bootstrap-core:$wicketBootstrapVersion")
    compile(project(":kwicket-wicket-bootstrap-core"))

    compile("de.agilecoders.wicket:wicket-bootstrap-extensions:$wicketBootstrapVersion")
    compile("de.agilecoders.wicket:wicket-bootstrap-themes:$wicketBootstrapVersion")

    //tstRuntime("org.junit.platform:junit-platform-launcher:$junitPlatformLauncherVersion")
    //testCompile("org.jetbrains.kotlin:kotlin-test:$kotlinTestVersion")
    //testRuntime("org.junit.vintage:junit-vintage-engine:$junitVintageEngineVersion")

}

tasks {
    // Disable publication on root project
    "artifactoryPublish"(ArtifactoryTask::class) {
        skip = true
    }
    "publishMavenPublicationToMavenRepository"(PublishToMavenLocal::class) {
        enabled = false
    }
}

