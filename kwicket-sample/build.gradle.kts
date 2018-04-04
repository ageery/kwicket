plugins {
    val kotlinVersion = "1.2.31"
    val bootVersion = "2.0.0.RELEASE"
    val dependencyManagementVersion = "1.0.4.RELEASE"
    id("org.jetbrains.kotlin.jvm") // version kotlinVersion
    id("io.spring.dependency-management") version dependencyManagementVersion
    id("org.jetbrains.kotlin.plugin.spring") version kotlinVersion
    id("org.springframework.boot") version bootVersion
}

//buildscript {
//    ext {
//        springBootVersion = "1.5.7.RELEASE"
//        junitPlatformVersion = "1.0.0"
//    }
//    repositories {
//        mavenCentral()
//    }
//    dependencies {
//        classpath("org.springframework.boot:spring-boot-gradle-plugin:$springBootVersion")
//        classpath("org.jetbrains.kotlin:kotlin-allopen:$kotlinVersion")
//        classpath("org.junit.platform:junit-platform-gradle-plugin:$junitPlatformVersion")
//    }
//}

//apply plugin: "org.springframework.boot"
//apply plugin: "kotlin-spring"
//apply plugin: "org.junit.platform.gradle.plugin"

val wicketVersion by project
val wicketStuffVersion by project
val wicketBootstrapVersion by project
val kWicketVersion by project
val kotlinxHtmlVersion by project

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

//bintray {
//    publications = []
//}