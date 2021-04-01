import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.dokka.gradle.DokkaTask
import org.gradle.api.tasks.testing.logging.TestLogEvent
import java.net.URL

plugins {
    kotlin("jvm") version "1.4.30"
    id("org.jetbrains.dokka") version "1.4.20"
    kotlin("plugin.serialization") version "1.4.30"
    id("io.gitlab.arturbosch.detekt") version "1.15.0"
    application
}

group = "me.user"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.1.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.4.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.1")
    testImplementation("org.junit.jupiter:junit-jupiter-params")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.1")
    implementation("com.squareup:kotlinpoet:1.6.0")
    implementation("com.charleskorn.kaml:kaml:0.29.0")

    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.14.2")
}

detekt {
    failFast = true // fail build on any finding
    config = files("config/detekt/detekt.yml")
    buildUponDefaultConfig = true
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events(
            TestLogEvent.STANDARD_ERROR,
            TestLogEvent.STARTED,
            TestLogEvent.PASSED,
            TestLogEvent.FAILED,
            TestLogEvent.SKIPPED
        )
    }
}

tasks.withType<KotlinCompile>() {
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf("-Werror")
    }
}

tasks.withType<DokkaTask>().configureEach {
    dokkaSourceSets {
        named("main") {
            moduleName.set("Kotlin Projects")
            sourceLink {
                localDirectory.set(file("src/main/kotlin"))
                remoteUrl.set(URL("https://github.com/martilut/KotlinProjects/tree/master/src/main/kotlin"))
                remoteLineSuffix.set("#L")
            }
        }
    }
}

application {
    mainClass.set("MainKt")
}