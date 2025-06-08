plugins {
    id(Plugins.library)
    id(Plugins.android)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = Core.uicommon.toBundle()
    compileSdk = ProjectConfig.compileSdk

    defaultConfig {
        minSdk = ProjectConfig.minSdk

        testInstrumentationRunner = ProjectConfig.TEST_INSTRUMENTATION_RUNNER
    }
    compileOptions {
        sourceCompatibility = ProjectConfig.javaVersion
        targetCompatibility = ProjectConfig.javaVersion
    }
    kotlinOptions {
        jvmTarget = ProjectConfig.jvmTarget
    }
    buildFeatures {
        compose = true
    }
}

composeCompiler {
    reportsDestination = layout.buildDirectory.dir("compose_compiler")
    stabilityConfigurationFile = rootProject.layout.projectDirectory.file("stability_config.conf")
}

dependencies {
    implementation(libs.androidx.junit.ktx)
    implementation(libs.androidx.monitor)
    coreTheme()
    coreBase()
    compose()
    coil()
    implementation(Dependencies.composeMaterial) //необходимо для темы
    implementation(Dependencies.composeActivity)
}