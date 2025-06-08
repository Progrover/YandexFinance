import org.gradle.api.JavaVersion

object ProjectConfig {
    const val appBundle = "dev.progrover.shmr_finance"
    const val minSdk = 26
    const val compileSdk = 35
    const val targetSdk = 35
    const val versionCode = 1
    const val versionName = "1.0"

    const val jvmTarget = "1.8"
    val javaVersion = JavaVersion.VERSION_1_8

    const val DEBUG = "debug"
    const val RELEASE = "release"
    const val TEST_INSTRUMENTATION_RUNNER = "androidx.test.runner.AndroidJUnitRunner"
    const val PROGUARD_FILE = "proguard-rules.pro"
    const val PROGUARD_DEFAULT = "proguard-android-optimize.txt"
}

fun String.toBundle() = ProjectConfig.appBundle + this.replace(':','.')