import org.kwicket.gradle.KwicketConfig.wicketVersion

dependencies {
    //val wicketVersion = org.kwicket.gradle.KwicketConfig.wicketVersion
    compile(project(":kwicket-core"))
    compile(project(":kwicket-wicket-core"))
    compileOnly("org.apache.wicket:wicket-extensions:$wicketVersion")
}