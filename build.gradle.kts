// Top-level build file where you can add configuration options common to all sub-projects/modules.
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

tasks {
    register("clean", Delete::class) {
        delete(rootProject.buildDir)
    }
}