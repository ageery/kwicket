dependencies {
    val kotlinxHtmlVersion = org.kwicket.gradle.KwicketConfig.kotlinxHtmlVersion
    compile(project(":kwicket-core"))
    compile(project(":kwicket-wicket-core"))
    compileOnly("org.jetbrains.kotlinx:kotlinx-html-jvm:$kotlinxHtmlVersion")
}