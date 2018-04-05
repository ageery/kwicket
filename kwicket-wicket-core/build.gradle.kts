// FIXME: pull the version numbers into vals...
dependencies {
    compile(project(":kwicket-core"))
    compileOnly("javax.servlet:javax.servlet-api:3.1.0")
    compileOnly("org.apache.wicket:wicket-core:8.0.0-M9")
}
