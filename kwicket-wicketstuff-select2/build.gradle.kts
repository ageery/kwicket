import org.kwicket.gradle.KwicketConfig.wicketStuffVersion
import org.kwicket.gradle.KwicketConfig.wicketVersion

dependencies {
    compile(project(":kwicket-core"))
    compileOnly(project(":kwicket-wicket-extensions"))
    compileOnly("org.wicketstuff:wicketstuff-select2:$wicketStuffVersion")
    compileOnly("org.apache.wicket:wicket-extensions:$wicketVersion")
}