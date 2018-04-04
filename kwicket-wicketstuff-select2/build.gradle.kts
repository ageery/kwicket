val wicketStuffVersion by project

plugins {
    `maven-publish`
}

dependencies {
    compile(project(":kwicket-core"))
    compileOnly("org.wicketstuff:wicketstuff-select2:$wicketStuffVersion")
}

val publicationName = "tlslib"
publishing {
    publications.invoke {
        publicationName(MavenPublication::class) {
//            artifactId = artifactID
//            artifact(shadowJar)
//            pom.addDependencies()
        }
    }
}

artifactory {  }