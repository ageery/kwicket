import org.kwicket.gradle.Versions.wicketStuffVersion
import org.kwicket.gradle.Versions.wicketVersion

dependencies {
    compile(project(":kwicket-core"))
    compileOnly(project(":kwicket-wicket-extensions"))
    compileOnly("org.wicketstuff:wicketstuff-select2:$wicketStuffVersion")
    compileOnly("org.apache.wicket:wicket-extensions:$wicketVersion")
}