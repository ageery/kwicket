import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getValue
import org.kwicket.gradle.Versions.servletApiVersion
import org.kwicket.gradle.Versions.springVersion
import org.kwicket.gradle.Versions.wicketVersion

dependencies {
    compileOnly("org.springframework:spring-web:$springVersion")
    compileOnly("javax.servlet:javax.servlet-api:$servletApiVersion")
    compileOnly("org.apache.wicket:wicket-spring:$wicketVersion")
}