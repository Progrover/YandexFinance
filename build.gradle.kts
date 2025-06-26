import io.gitlab.arturbosch.detekt.Detekt

// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    alias(libs.plugins.detekt)
}

buildscript {

    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }

    dependencies {
        classpath(Dependencies.hiltAgp)
        classpath(Dependencies.kotlinSerialization)
        classpath(libs.kotlin.gradle.plugin)
    }
}

dependencies {
    detektPlugins(libs.detekt.formatting)
}

detekt {
    toolVersion = libs.versions.detekt.get()
    config.setFrom(file(File(rootDir, "config/detekt/detekt.yml")))
    buildUponDefaultConfig = true
}
tasks.withType<Detekt>().configureEach {
    reports {
        xml.required.set(true)
        html.required.set(true)
        sarif.required.set(true)
        md.required.set(true)
    }
    parallel = true
    autoCorrect = false
    basePath = rootProject.projectDir.absolutePath

    setSource(files(projectDir))
    include("**/*.kt")
    include("**/*.kts")
    exclude("**/resources/**")
    exclude("**/build/**")
}

tasks {
    register("clean", Delete::class) {
        delete(rootProject.buildDir)
    }
}