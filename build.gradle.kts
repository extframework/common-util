plugins {
    kotlin("jvm") version "1.6.21"

    id("signing")
    id("maven-publish")
    id("org.jetbrains.dokka") version "1.6.21"
    id("org.javamodularity.moduleplugin") version "1.8.10"
}

group = "net.yakclient"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

tasks.wrapper {
    gradleVersion = "7.2"
}

kotlin {
    explicitApi()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")
}

tasks.compileKotlin {
    destinationDirectory.set(tasks.compileJava.get().destinationDirectory.asFile.get())

    kotlinOptions {
        jvmTarget = "17"
    }
}

tasks.compileTestKotlin {
    kotlinOptions {
        jvmTarget = "17"
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.compileJava {
    targetCompatibility = "17"
    sourceCompatibility = "17"
}

task<Jar>("sourcesJar") {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

task<Jar>("javadocJar") {
    archiveClassifier.set("javadoc")
    from(tasks.dokkaJavadoc)
}

publishing {
    publications {
        create<MavenPublication>("common-util-maven") {
            from(components["java"])
            artifact(tasks["sourcesJar"])
            artifact(tasks["javadocJar"])

            artifactId = "common-util"

            pom {
                name.set("Common Util")
                description.set("Common utilities for all of YakClient")
                url.set("https://github.com/yakclient/common-util")

                packaging = "jar"

                withXml {
                    val repositoriesNode = asNode().appendNode("repositories")
                    val yakclientRepositoryNode = repositoriesNode.appendNode("repository")
                    yakclientRepositoryNode.appendNode("id", "yakclient")
                    yakclientRepositoryNode.appendNode("url", "http://maven.yakclient.net/snapshots")
                }

                developers {
                    developer {
                        id.set("Chestly")
                        name.set("Durgan McBroom")
                    }
                }

                licenses {
                    license {
                        name.set("GNU General Public License")
                        url.set("https://opensource.org/licenses/gpl-license")
                    }
                }

                scm {
                    connection.set("scm:git:git://github.com/yakclient/common-util")
                    developerConnection.set("scm:git:ssh://github.com:yakclient/common-util.git")
                    url.set("https://github.com/yakclient/common-util")
                }
            }
        }
    }

    repositories {
        if (!project.hasProperty("maven-user") || !project.hasProperty("maven-pass")) return@repositories

        maven {
            val repo = if (project.findProperty("isSnapshot") == "true") "snapshots" else "releases"

            isAllowInsecureProtocol = true

            url = uri("http://repo.yakclient.net/$repo")

            credentials {
                username = project.findProperty("maven-user") as String
                password = project.findProperty("maven-pass") as String
            }
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }
}