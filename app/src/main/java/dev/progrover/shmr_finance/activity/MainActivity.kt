package dev.progrover.shmr_finance.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SwipeableDefaults
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.material.BottomSheetNavigator
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import dev.progrover.core.base.data.storage.Prefs
import dev.progrover.core.base.di.NavigationFactoryQualifiers
import dev.progrover.core.base.navigation.NavigationFactory
import dev.progrover.core.base.utils.CURRENT_MAIN_COLOR
import dev.progrover.core.base.utils.ColorVariant
import dev.progrover.core.base.utils.LANGUAGE
import dev.progrover.core.base.utils.LocaleVariant
import dev.progrover.core.base.utils.SettingsOptions
import dev.progrover.core.base.utils.THEME_MODE_DARK
import dev.progrover.core.theme.AppThemeComposable
import dev.progrover.shmr_finance.MainApplication
import dev.progrover.shmr_finance.di.ApplicationComponent
import dev.progrover.shmr_finance.navigation.host.FinanceNavigation
import dev.progrover.shmr_finance.viewmodel.MainActivityViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

/**
 * Главный Actitivy приложения
 */
class MainActivity : ComponentActivity() {

    private lateinit var appComponent: ApplicationComponent

    private lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var prefs: Prefs

    private val viewModel: MainActivityViewModel by viewModels { viewModelFactory }

    @Inject
    @NavigationFactoryQualifiers.MainActivity
    lateinit var navigationFactories: Set<@JvmSuppressWildcards NavigationFactory>

    @RequiresApi(Build.VERSION_CODES.S)
    @OptIn(ExperimentalMaterialNavigationApi::class, DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var animationEnd = (applicationContext as MainApplication).splashAnimationEnd

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                !animationEnd
            }
        }
        GlobalScope.launch {
            if (!animationEnd)
                delay(2000)
            animationEnd = true
            (applicationContext as MainApplication).setAnimationEnd()
        }
        appComponent = (application as MainApplication).getApplicationComponent()
        appComponent.inject(this)

        subscribeOnSettingsChanges()

        viewModelFactory = appComponent.getMainViewModelFactory()

        WindowCompat.setDecorFitsSystemWindows(window, false)
        orientationRequest()
        setContent {
            AppThemeComposable(
                darkTheme = prefs.getBool(THEME_MODE_DARK),
                darkStatusBarIcons = !prefs.getBool(THEME_MODE_DARK),
                mainColorVariant = prefs.getString(
                    CURRENT_MAIN_COLOR,
                    SettingsOptions.colorVariants[ColorVariant.Green]
                ) ?: SettingsOptions.colorVariants[ColorVariant.Green]!!
            ) {
                val scaffoldState: ScaffoldState = rememberScaffoldState()
                val bottomSheetNavigator = rememberBottomSheetNavigator()
                val navController = rememberNavController(bottomSheetNavigator)

                FinanceNavigation(
                    viewModel = viewModel,
                    bottomSheetNavigator = bottomSheetNavigator,
                    scaffoldState = scaffoldState,
                    navController = navController,
                    navigationFactories = navigationFactories,
                )
            }
        }
    }

    //Обновление локали при изменении в настройках
    override fun attachBaseContext(newBase: Context) {
        val prefs = newBase.getSharedPreferences("cookiePrefs", MODE_PRIVATE)
        val default = SettingsOptions.localeVariants[LocaleVariant.Russian]!!
        val language = prefs.getString(LANGUAGE, default) ?: default
        val locale = Locale(language)

        val config = Configuration(newBase.resources.configuration)
        Locale.setDefault(locale)
        config.setLocale(locale)
        val context = newBase.createConfigurationContext(config)
        super.attachBaseContext(context)
    }

    private fun applySelectedAppLanguage(context: Context): Context {
        val default = SettingsOptions.localeVariants[LocaleVariant.Russian]!!
        val locale = Locale(
            prefs.getString(LANGUAGE, default)
                ?: default
        )
        val newConfig = Configuration(context.resources.configuration)
        Locale.setDefault(locale)
        newConfig.setLocale(locale)
        return context.createConfigurationContext(newConfig)
    }

    @OptIn(ExperimentalMaterialNavigationApi::class)
    @Composable
    fun rememberBottomSheetNavigator(
        animationSpec: AnimationSpec<Float> = SwipeableDefaults.AnimationSpec
    ): BottomSheetNavigator {
        val sheetState = rememberModalBottomSheetState(
            initialValue = ModalBottomSheetValue.Hidden,
            animationSpec = animationSpec,
            skipHalfExpanded = true
        )
        return remember(sheetState) {
            BottomSheetNavigator(sheetState = sheetState)
        }
    }

    private fun orientationRequest() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LOCKED
    }

    private fun reloadActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        this.startActivity(intent)
        this.finish()
    }

    private fun subscribeOnSettingsChanges() {
        prefs.registerOnSharedPreferenceChangeListener(settingsListener)
    }

    private val settingsListener =
        SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            when (key) {
                LANGUAGE ->
                    reloadActivity()

                THEME_MODE_DARK ->
                    reloadActivity()

                CURRENT_MAIN_COLOR ->
                    reloadActivity()
            }
        }
}