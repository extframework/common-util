import dev.extframework.gradle.common.dm.resourceApi
import dev.extframework.gradle.common.extFramework

plugins {
    kotlin("jvm") version "1.9.21"

    id("dev.extframework.common") version "1.0.43"
}

group = "dev.extframework"
version = "1.2.1-SNAPSHOT"

repositories {
    mavenCentral()
    extFramework()
}

tasks.wrapper {
    gradleVersion = "7.2"
}

kotlin {
    explicitApi()
}

dependencies {
    resourceApi(configurationName = "api")

    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")

    testImplementation(kotlin("test"))
}

common {
    defaultJavaSettings()
    publishing {
        publication {
            withJava()
            withSources()
            withDokka()

            this.version = project.version as String

            artifactId = "common-util"

            commonPom {
                name.set("Common Util")
                description.set("Common utilities for all of YakClient")
                url.set("https://github.com/extframework/common-util")

                packaging = "jar"

                withExtFrameworkRepo()

                defaultDevelopers()
                gnuLicense()
                extFrameworkScm("common-util")
            }
        }
        this.repositories {
            extFramework(credentials = propertyCredentialProvider)
        }
    }

    java {
        toolchain {
            version = JavaVersion.VERSION_1_8
        }
    }
}