package dev.progrover.shmr_finance.navigation.host

import android.annotation.SuppressLint
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.google.accompanist.navigation.material.BottomSheetNavigator
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import dev.progrover.core.base.navigation.NavigationFactory
import dev.progrover.core.base.utils.LocalVibrationType
import dev.progrover.core.theme.AppTheme
import dev.progrover.expenditures.api.ExpendituresFeature
import dev.progrover.shmr_finance.navigation.components.BottomNavigationBar
import dev.progrover.shmr_finance.viewmodel.MainActivityViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
internal fun FinanceNavigation(
    viewModel: MainActivityViewModel,
    bottomSheetNavigator: BottomSheetNavigator,
    scaffoldState: ScaffoldState,
    navController: NavHostController,
    navigationFactories: @JvmSuppressWildcards Set<NavigationFactory>,
) {
    val uiState by viewModel.uiState.collectAsState()

    CompositionLocalProvider(
        LocalVibrationType provides uiState.vibrationMode
    ) {
        ModalBottomSheetLayout(
            bottomSheetNavigator = bottomSheetNavigator,
            sheetBackgroundColor = AppTheme.colors.white,
            scrimColor = Color.Black.copy(alpha = 0.4f),
            sheetShape = RoundedCornerShape(
                topStart = AppTheme.paddings.padding32,
                topEnd = AppTheme.paddings.padding32
            ),
        ) {
            Scaffold(
                scaffoldState = scaffoldState,
                bottomBar = {
                    BottomNavigationBar(uiState = uiState, navController = navController)
                },
            ) {
                NavHost(
                    navController = navController,
                    startDestination = ExpendituresFeature.ROUTE_NAME
                ) {
                    navigationFactories.forEach { factory ->
                        factory.create(this, navController = navController)
                    }
                }
            }
        }
    }
}