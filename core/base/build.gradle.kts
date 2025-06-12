plugins {
    id(Plugins.library)
    id(Plugins.android)
    id(Plugins.hilt)
    kotlin(Plugins.kapt)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = Core.base.toBundle()
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

    restApiMonitoring()
    hilt()
    viewModel()
    coroutines()
    navigation()
    retrofit()
    timber()
    coil()
    work()
    securityCrypto()
}

kapt {
    correctErrorTypes = true
}