import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.repositories

repositories {
    mavenCentral()
}

plugins {
    kotlin("jvm") version "1.2.31"
    //application
}

dependencies {
    compile("com.squareup:kotlinpoet:0.7.0")
    compile("org.apache.wicket:wicket-core:8.0.0-M9")
    compile("com.google.auto:auto-common:0.10")
}

repositories {
    jcenter()
    mavenCentral()
}

