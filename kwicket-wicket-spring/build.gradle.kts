import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getValue

val springVersion by project
val servletApiVersion by project
val wicketVersion by project

dependencies {
    compileOnly("org.springframework:spring-web:$springVersion")
    compileOnly("javax.servlet:javax.servlet-api:$servletApiVersion")
    compileOnly("org.apache.wicket:wicket-spring:$wicketVersion")
}