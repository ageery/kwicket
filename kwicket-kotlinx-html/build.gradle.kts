val kotlinxHtmlVersion by project

dependencies {
    compile(project(":kwicket-core"))
    compile(project(":kwicket-wicket-core"))
    compileOnly("org.jetbrains.kotlinx:kotlinx-html-jvm:$kotlinxHtmlVersion")
}