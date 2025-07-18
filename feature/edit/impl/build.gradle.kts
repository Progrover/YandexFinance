plugins {
    id(Plugins.library)
    id(Plugins.android)
    kotlin(Plugins.kapt)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = Feature.Edit.impl.toBundle()
    compileSdk = ProjectConfig.compileSdk

    defaultConfig {
        minSdk = ProjectConfig.minSdk

        testInstrumentationRunner = ProjectConfig.TEST_INSTRUMENTATION_RUNNER
    }

    compileOptions {
        sourceCompatibility = ProjectConfig.javaVersion
        targetCompatibility = ProjectConfig.javaVersion
        isCoreLibraryDesugaringEnabled = false
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

    implementation(libs.androidx.monitor)

    coreBase()
    coreTheme()
    coreUiCommon()

    room()
    dagger()
    compose()
    navigation()
    coil()
    editApi()
    accountApi()
    historyApi()
    incomesApi()
    editApi()
    articlesApi()
    // encrypt
    implementation(libs.jbcrypt)
}

kapt {
    correctErrorTypes = true
}