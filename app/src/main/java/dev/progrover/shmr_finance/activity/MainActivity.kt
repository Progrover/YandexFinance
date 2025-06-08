package dev.progrover.shmr_finance.activity

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SwipeableDefaults
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.material.BottomSheetNavigator
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import dagger.hilt.android.AndroidEntryPoint
import dev.progrover.core.base.data.storage.Prefs
import dev.progrover.core.base.di.NavigationFactoryQualifiers
import dev.progrover.core.base.navigation.NavigationFactory
import dev.progrover.core.theme.AppThemeComposable
import dev.progrover.shmr_finance.navigation.host.FinanceNavigation
import dev.progrover.shmr_finance.viewmodel.MainActivityViewModel
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    @NavigationFactoryQualifiers.MainActivity
    lateinit var navigationFactories: @JvmSuppressWildcards Set<NavigationFactory>

    @Inject
    lateinit var prefs: Prefs

    private val viewModel by viewModels<MainActivityViewModel>()

    @OptIn(ExperimentalMaterialNavigationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        orientationRequest()
        setContent {

            AppThemeComposable {
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
}