dependencies {
    val wicketBootstrapVersion = org.kwicket.gradle.KwicketConfig.wicketBootstrapVersion
    compile(project(":kwicket-core"))
    compile(project(":kwicket-wicket-core"))
    compileOnly("de.agilecoders.wicket:wicket-bootstrap-core:$wicketBootstrapVersion")
}