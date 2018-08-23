import org.kwicket.gradle.Versions.wicketVersion
import org.kwicket.gradle.Versions.servletApiVersion

dependencies {
    compile(project(":kwicket-core"))
    compileOnly("javax.servlet:javax.servlet-api:$servletApiVersion")
    compileOnly("org.apache.wicket:wicket-core:$wicketVersion")
}
