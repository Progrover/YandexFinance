plugins {
    id(Plugins.application)
    id(Plugins.android)
    kotlin(Plugins.kapt)
    id(Plugins.hilt)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = ProjectConfig.appBundle
    compileSdk = ProjectConfig.compileSdk

    defaultConfig {
        applicationId = ProjectConfig.appBundle
        minSdk = ProjectConfig.minSdk
        targetSdk = ProjectConfig.targetSdk
        versionCode = ProjectConfig.versionCode
        versionName = ProjectConfig.versionName

        testInstrumentationRunner = ProjectConfig.TEST_INSTRUMENTATION_RUNNER
        vectorDrawables {
            useSupportLibrary = true
        }
        buildFeatures {
            buildConfig = true
        }
    }

    buildTypes {
        getByName(ProjectConfig.RELEASE) {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false
            proguardFiles(
                getDefaultProguardFile(ProjectConfig.PROGUARD_DEFAULT),
                ProjectConfig.PROGUARD_FILE
            )
        }
        getByName(ProjectConfig.DEBUG) {
            isDebuggable = true
            isMinifyEnabled = false
            applicationIdSuffix = ""
            versionNameSuffix = "-DEV"
            signingConfig = signingConfigs.getByName(ProjectConfig.DEBUG)
        }
    }
    applicationVariants.all {
        val variant = this
        variant.outputs
            .map { it as com.android.build.gradle.internal.api.BaseVariantOutputImpl }
            .forEach { output ->
                val outputFileName =
                    "SHMR FINANCE build ${variant.baseName} - ${variant.versionName} ${variant.versionCode}.apk"
                output.outputFileName = outputFileName
            }
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

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

composeCompiler {
    reportsDestination = layout.buildDirectory.dir("compose_compiler")
    stabilityConfigurationFile = rootProject.layout.projectDirectory.file("stability_config.conf")
}

dependencies {

    coreBase()
    coreTheme()
    coreUiCommon()

    base()
    navigation()
    compose()
    hilt()
    coil()
    work()
    coroutinesPlayServices()

    incomesApi()
    expendituresApi()
    accountApi()
    articlesApi()
    settingsApi()

    implementation(project(Feature.Incomes.impl))
    implementation(project(Feature.Expenditures.impl))
    implementation(project(Feature.Account.impl))
    implementation(project(Feature.Articles.impl))
    implementation(project(Feature.Settings.impl))
}

kapt {
    correctErrorTypes = true
}