import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getValue
import org.kwicket.gradle.KwicketConfig.servletApiVersion
import org.kwicket.gradle.KwicketConfig.springVersion
import org.kwicket.gradle.KwicketConfig.wicketVersion

dependencies {
    compileOnly("org.springframework:spring-web:$springVersion")
    compileOnly("javax.servlet:javax.servlet-api:$servletApiVersion")
    compileOnly("org.apache.wicket:wicket-spring:$wicketVersion")
}