import groovy.lang.GroovyObject
import org.gradle.api.internal.artifacts.configurations.DefaultConfiguration
import org.jfrog.build.extractor.clientConfiguration.ArtifactoryClientConfiguration
import org.jfrog.gradle.plugin.artifactory.dsl.PublisherConfig

val wicketVersion by project

dependencies {
    compile(project(":kwicket-core"))
    compile(project(":kwicket-wicket-core"))
    compileOnly("org.apache.wicket:wicket-extensions:$wicketVersion")
}