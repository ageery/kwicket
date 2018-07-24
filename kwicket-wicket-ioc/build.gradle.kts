import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getValue
import org.kwicket.gradle.KwicketConfig.servletApiVersion
import org.kwicket.gradle.KwicketConfig.springVersion
import org.kwicket.gradle.KwicketConfig.wicketVersion

dependencies {
    compileOnly("org.apache.wicket:wicket-ioc:$wicketVersion")
}