import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.changelog.closure
import org.jetbrains.changelog.markdownToHTML
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.4.10"
    id("org.jetbrains.intellij") version "0.4.22"
    id("org.jetbrains.changelog") version "0.6.2"
    id("io.gitlab.arturbosch.detekt") version "1.13.1"
    id("org.jlleitschuh.gradle.ktlint") version "9.4.0"
}

val pluginGroup: String by project
val pluginName_: String by project
val pluginVersion: String by project
val pluginSinceBuild: String by project
val pluginUntilBuild: String by project

val platformType: String by project
val platformVersion: String by project
val platformDownloadSources: String by project

group = pluginGroup
version = pluginVersion

repositories {
    mavenCentral()
    jcenter()
}
dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.4.10")
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.13.1")

    listOf("fuel", "fuel-coroutines", "fuel-jackson").forEach {
        implementation(group = "com.github.kittinunf.fuel", name = it, version = "2.2.3")
    }
    implementation("org.freemarker:freemarker:2.3.30")
    implementation("com.github.doyaaaaaken:kotlin-csv-jvm:0.11.1")
}

intellij {
    pluginName = pluginName_
    version = platformVersion
    type = platformType
    downloadSources = platformDownloadSources.toBoolean()
    updateSinceUntilBuild = true
}

detekt {
    config = files("./detekt-config.yml")
    buildUponDefaultConfig = true

    reports {
        html.enabled = false
        xml.enabled = false
        txt.enabled = false
    }
}

tasks {
    withType<JavaCompile> {
        sourceCompatibility = "1.8"
        targetCompatibility = "1.8"
    }
    listOf("compileKotlin", "compileTestKotlin").forEach {
        getByName<KotlinCompile>(it) {
            kotlinOptions.jvmTarget = "1.8"
        }
    }

    withType<Detekt> {
        jvmTarget = "1.8"
    }

    patchPluginXml {
        version(pluginVersion)
        sinceBuild(pluginSinceBuild)
        untilBuild(pluginUntilBuild)

        // Extract the <!-- Plugin description --> section from README.md and provide for the plugin's manifest
        pluginDescription(
            closure {
                File("./README.md").readText().lines().run {
                    val start = "<!-- Plugin description -->"
                    val end = "<!-- Plugin description end -->"

                    if (!containsAll(listOf(start, end))) {
                        throw GradleException("Plugin description section not found in README.md file:\n$start ... $end")
                    }
                    subList(indexOf(start) + 1, indexOf(end))
                }.joinToString("\n").run { markdownToHTML(this) }
            }
        )

        changeNotes(
            closure {
                changelog.getLatest().toHTML()
            }
        )
    }

    publishPlugin {
        token(System.getenv("PUBLISH_TOKEN"))
        channels(pluginVersion.split('-').getOrElse(1) { "default" }.split('.').first())
    }
}
