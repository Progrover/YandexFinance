import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project

object Dependencies {
    const val kotlinSerialization = "org.jetbrains.kotlin:kotlin-serialization:${Versions.kotlin}"

    const val composeMaterial3 = "androidx.compose.material3:material3:${Versions.composeMaterial3}"
    const val composeMaterial = "androidx.compose.material:material:${Versions.compose}"
    const val composeUi = "androidx.compose.ui:ui:${Versions.compose}"
    const val composeUiGraphics = "androidx.compose.ui:ui-graphics:${Versions.compose}"
    const val composeUiTooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
    const val composeUiToolingPreview = "androidx.compose.ui:ui-tooling-preview:${Versions.compose}"
    const val composeRuntime = "androidx.compose.runtime:runtime:${Versions.compose}"
    const val composeCompiler = "androidx.compose.compiler:compiler:${Versions.composeCompiler}"
    const val composeFundation = "androidx.compose.foundation:foundation:${Versions.compose}"
    const val composeActivity = "androidx.activity:activity-compose:${Versions.composeActivity}"

    const val swipeRefresh =
        "com.google.accompanist:accompanist-swiperefresh:${Versions.swipeRefresh}"
    const val uiController =
        "com.google.accompanist:accompanist-systemuicontroller:${Versions.accompanist}"

    const val daggerAndroid = "com.google.dagger:dagger:${Versions.dagger}"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"

    //room
    const val room = "androidx.room:room-ktx:${Versions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"

    const val workRuntime = "androidx.work:work-runtime-ktx:${Versions.workRuntime}"

    const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    const val okHttpLoggerInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
    const val okHttpUrlConnection = "com.squareup.okhttp3:okhttp-urlconnection:${Versions.okhttp}"

    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val moshiConvertor = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
    const val moshiAdapters = "com.squareup.moshi:moshi-adapters:${Versions.moshi}"
    const val moshiKotlin = "com.squareup.moshi:moshi-kotlin:${Versions.moshi}"

    const val navigationCompose =
        "androidx.navigation:navigation-compose:${Versions.navigationCompose}"
    const val navigationMaterial =
        "com.google.accompanist:accompanist-navigation-material:${Versions.navigationMaterial}"

    const val coil = "io.coil-kt:coil-compose:${Versions.coil}"

    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val coroutinesPlayServices =
        "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:${Versions.coroutines}"

    const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val viewModelCompose =
        "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.lifecycle}"

    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"

    const val lifecycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
    const val lifecycleProcess = "androidx.lifecycle:lifecycle-process:${Versions.lifecycle}"

    const val debugChucker = "com.github.chuckerteam.chucker:library:${Versions.chucker}"
    const val releaseChucker = "com.github.chuckerteam.chucker:library-no-op:${Versions.chucker}"

    const val securityCrypto = "androidx.security:security-crypto-ktx:${Versions.securityCrypto}"
}

fun DependencyHandler.restApiMonitoring() {
    debugImplementation(Dependencies.debugChucker)
    releaseImplementation(Dependencies.releaseChucker)
}

fun DependencyHandler.retrofit() {
    api(Dependencies.retrofit)
    implementation(Dependencies.okHttp)
    implementation(Dependencies.okHttpLoggerInterceptor)
    implementation(Dependencies.okHttpUrlConnection)
    api(Dependencies.moshiKotlin)
    implementation(Dependencies.moshiConvertor)
    implementation(Dependencies.moshiAdapters)
}

fun DependencyHandler.timber() {
    api(Dependencies.timber)
}

fun DependencyHandler.dagger() {
    implementation(Dependencies.daggerAndroid)
    kapt(Dependencies.daggerCompiler)
}

fun DependencyHandler.room() {
    implementation(Dependencies.room)
    implementation(Dependencies.roomRuntime)
    kapt(Dependencies.roomCompiler)
}

fun DependencyHandler.work() {
    implementation(Dependencies.workRuntime)
}

fun DependencyHandler.securityCrypto() {
    implementation(Dependencies.securityCrypto)
}

fun DependencyHandler.navigation() {
    implementation(Dependencies.navigationCompose)
    implementation(Dependencies.navigationMaterial)
}

fun DependencyHandler.coroutines() {
    implementation(Dependencies.coroutines)
}

fun DependencyHandler.coroutinesPlayServices() {
    implementation(Dependencies.coroutinesPlayServices)
}

fun DependencyHandler.viewModel() {
    implementation(Dependencies.viewModel)
    implementation(Dependencies.viewModelCompose)
}

fun DependencyHandler.compose() {
    implementation(Dependencies.composeUi)
    implementation(Dependencies.composeMaterial3)
    implementation(Dependencies.composeUiTooling)
    debugImplementation(Dependencies.composeUiToolingPreview)
    implementation(Dependencies.composeRuntime)
    implementation(Dependencies.composeUiGraphics)
    implementation(Dependencies.composeMaterial)
    implementation(Dependencies.swipeRefresh)
    implementation(Dependencies.composeFundation)
}

fun DependencyHandler.base() {
    implementation(Dependencies.lifecycleRuntimeKtx)
    implementation(Dependencies.lifecycleProcess)
    implementation(Dependencies.composeActivity)
}

fun DependencyHandler.coil() {
    implementation(Dependencies.coil)
}

fun DependencyHandler.coreBase() {
    implementation(project(Core.base))
}

fun DependencyHandler.coreTheme() {
    implementation(project(Core.theme))
}

fun DependencyHandler.coreUiCommon() {
    implementation(project(Core.uicommon))
}

fun DependencyHandler.incomesApi() {
    implementation(project(Feature.Incomes.api))
}

fun DependencyHandler.accountApi() {
    implementation(project(Feature.Account.api))
}

fun DependencyHandler.expendituresApi() {
    implementation(project(Feature.Expenditures.api))
}

fun DependencyHandler.settingsApi() {
    implementation(project(Feature.Settings.api))
}

fun DependencyHandler.articlesApi() {
    implementation(project(Feature.Articles.api))
}

fun DependencyHandler.historyApi() {
    implementation(project(Feature.History.api))
}

fun DependencyHandler.editApi() {
    implementation(project(Feature.Edit.api))
}

fun DependencyHandler.authApi() {
    implementation(project(Feature.Auth.api))
}