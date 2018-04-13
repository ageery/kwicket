import com.jfrog.bintray.gradle.BintrayExtension
import com.sun.xml.internal.ws.resources.WsservletMessages
import groovy.lang.GroovyObject
import org.gradle.api.internal.artifacts.configurations.DefaultConfiguration
import org.gradle.api.internal.plugins.DefaultArtifactPublicationSet
import org.jetbrains.kotlin.gradle.dsl.Coroutines.ENABLE
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.gradle.jvm.tasks.Jar
import org.jetbrains.dokka.DokkaConfiguration
import org.jetbrains.dokka.ExternalDocumentationLinkImpl
import org.jetbrains.dokka.gradle.DokkaTask
import org.jfrog.build.extractor.clientConfiguration.ArtifactoryClientConfiguration
import org.jfrog.gradle.plugin.artifactory.ArtifactoryPlugin
import org.jfrog.gradle.plugin.artifactory.dsl.ArtifactoryPluginConvention
import org.jfrog.gradle.plugin.artifactory.dsl.PublisherConfig
import org.jfrog.gradle.plugin.artifactory.task.ArtifactoryTask
import java.net.URL

buildscript {

    val kotlinVersion = "1.2.31"
    val dokkaVersion = "0.9.16"
    val bintrayVersion = "1.7.3"
    val artifactoryVersion = "4.5.2"

    repositories {
        mavenCentral()
        jcenter()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("com.jfrog.bintray.gradle:gradle-bintray-plugin:$bintrayVersion")
        classpath("org.jetbrains.dokka:dokka-gradle-plugin:$dokkaVersion")
        classpath("org.jfrog.buildinfo:build-info-extractor-gradle:$artifactoryVersion")
    }

}

plugins {
    val kotlinVersion = "1.2.31"
    id("org.jetbrains.kotlin.jvm") version kotlinVersion
}

allprojects {
    group = "org.kwicket"
    version = "0.0.3-SNAPSHOT"
}

subprojects {

    val kotlinVersion by project
    val wicketVersion by project
    val kotlinCoroutinesVersion by project

    val javadocLoc = "$buildDir/javadoc"
    val mavenPubName = "mavenJavaLibrary"

    val bintrayUser = if (project.hasProperty("bintrayUser")) project.property("bintrayUser").toString()
    else System.getenv("BINTRAY_USER")
    val bintrayKey = if (project.hasProperty("bintrayKey")) project.property("bintrayKey").toString()
    else System.getenv("BINTRAY_API_KEY")

    println("bintrayUser=$bintrayUser, bintrayKey=$bintrayKey")

    plugins.apply("org.jetbrains.kotlin.jvm")
    plugins.apply("com.jfrog.bintray")
    plugins.apply("org.jetbrains.dokka")
    plugins.apply("com.jfrog.artifactory")
    plugins.apply("maven")
    plugins.apply("maven-publish")

    tasks.withType<KotlinCompile> {
        kotlin {
            experimental.coroutines = ENABLE
        }
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs = listOf("-Xjsr305=strict")
        }
    }

    tasks.withType<JavaCompile> {
        java.sourceSets["main"].resources {
            srcDir("src/main/kotlin")
            srcDir("src/test/kotlin")
        }
    }

    tasks.withType<Jar> {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }

    val sourcesJar by tasks.creating(Jar::class) {
        classifier = "sources"
        from(java.sourceSets["main"].allSource)
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }

    val dokkaJavadocTask by tasks.creating(DokkaTask::class) {
        outputFormat = "javadoc"
        outputDirectory = javadocLoc
        externalDocumentationLink(delegateClosureOf<DokkaConfiguration.ExternalDocumentationLink.Builder> {
            url = URL("https://ci.apache.org/projects/wicket/apidocs/8.x/")
        })
    }

    val javadocJar by tasks.creating(Jar::class) {
        dependsOn(dokkaJavadocTask)
        classifier = "javadoc"
        from(javadocLoc)
    }

    configure<BintrayExtension> {
        user = bintrayUser
        key = bintrayKey
        setPublications("mavenJavaLibrary")
        publish = true
        pkg(delegateClosureOf<BintrayExtension.PackageConfig> {
            repo = "maven"
            name = "kwicket"
            setLicenses("Apache-2.0")
            vcsUrl = "https://github.com/ageery/kwicket.git"
            githubRepo = "ageery/kwicket"
            githubReleaseNotesFile = "README.md"
            version(delegateClosureOf<BintrayExtension.VersionConfig> {
                name = "$version"
                vcsTag = "kwicket-$version"
            })
        })
    }

    configure<PublishingExtension> {
        publications {
            create<MavenPublication>(mavenPubName) {
                from(components.getByName("java"))
                artifact(sourcesJar)
                artifact(javadocJar)
            }
        }
    }

    repositories {
        mavenCentral()
        jcenter()
    }

    dependencies {
        compileOnly("org.apache.wicket:wicket-core:$wicketVersion")
        compile("org.jetbrains.kotlin:kotlin-stdlib-jre8:$kotlinVersion")
        compile("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
        compile("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinCoroutinesVersion")
    }

    fun Project.artifactory(configure: ArtifactoryPluginConvention.() -> Unit): Unit =
        configure(project.convention.getPluginByName<ArtifactoryPluginConvention>("artifactory"))

    artifactory {
        setContextUrl("https://oss.jfrog.org/artifactory")
        publish(delegateClosureOf<PublisherConfig> {
            repository(delegateClosureOf<GroovyObject> {
                setProperty("repoKey", "oss-snapshot-local")
                setProperty("username", bintrayUser)
                setProperty("password", bintrayKey)
                setProperty("maven", true)
            })
            defaults(delegateClosureOf<GroovyObject> {
                invokeMethod("publications", mavenPubName)
                setProperty("publishArtifacts", true)
                setProperty("publishPom", true)
            })
        })
    }

}
