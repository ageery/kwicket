import org.kwicket.gradle.Versions.wicketVersion

dependencies {
    compile(project(":kwicket-core"))
    compile(project(":kwicket-wicket-core"))
    compileOnly("org.apache.wicket:wicket-extensions:$wicketVersion")
}