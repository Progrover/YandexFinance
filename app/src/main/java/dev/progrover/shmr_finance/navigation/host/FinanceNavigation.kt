package dev.progrover.shmr_finance.navigation.host

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.google.accompanist.navigation.material.BottomSheetNavigator
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import dev.progrover.core.base.navigation.NavigationFactory
import dev.progrover.core.theme.AppTheme
import dev.progrover.incomes.api.IncomesFeature
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

    ModalBottomSheetLayout(
        bottomSheetNavigator = bottomSheetNavigator,
        sheetBackgroundColor = AppTheme.colors.surface,
        scrimColor = AppTheme.colors.surface.copy(alpha = 0.4f),
    ) {

        Scaffold(
            scaffoldState = scaffoldState,
            bottomBar = {
                BottomNavigationBar(uiState = uiState, navController = navController)
            },
        ) {

            NavHost(
                navController = navController,
                startDestination = IncomesFeature.ROUTE_NAME
            ) {
                navigationFactories.forEach { factory ->
                    factory.create(this, navController = navController)
                }
            }
        }
    }
}