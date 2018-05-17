val wicketBootstrapVersion by project

dependencies {
    compile(project(":kwicket-core"))
    compile(project(":kwicket-wicket-core"))
    compile(project(":kwicket-wicket-bootstrap-core"))
    compileOnly("de.agilecoders.wicket:wicket-bootstrap-extensions:$wicketBootstrapVersion")
}