plugins {
    id(Plugins.library)
    id(Plugins.android)
    id(Plugins.hilt)
    kotlin(Plugins.kapt)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = Feature.Incomes.impl.toBundle()
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

    hilt()
    compose()
    navigation()
    coil()
    incomesApi()
    //splash screen
    implementation("androidx.core:core-splashscreen:1.0.1")

    //encrypt
    implementation("org.mindrot:jbcrypt:0.4")
}

//kapt {
//    correctErrorTypes = true
//}