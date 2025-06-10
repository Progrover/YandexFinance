package dev.progrover.shmr_finance.navigation.model

import dev.progrover.account.api.AccountFeature
import dev.progrover.articles.api.ArticlesFeature
import dev.progrover.expenditures.api.ExpendituresFeature
import dev.progrover.incomes.api.IncomesFeature
import dev.progrover.settings.api.SettingsFeature
import dev.progrover.shmr_finance.R

//todo: добавить маршруты после реализации основных фич
sealed class BottomNavigationItem(
    val route: String,
    val iconResId: Int,
    val caption: Int,
) {

    data object IncomesScreen : BottomNavigationItem(
        route = IncomesFeature.ROUTE_NAME,
        iconResId = R.drawable.uptrend,
        caption = R.string.incomes,
    )

    data object ExpendituresScreen : BottomNavigationItem(
        route = ExpendituresFeature.ROUTE_NAME,
        iconResId = R.drawable.downtrend,
        caption = R.string.expenditures,
    )

    data object AccountScreen : BottomNavigationItem(
        route = AccountFeature.ROUTE_NAME,
        iconResId = R.drawable.account,
        caption = R.string.account,
    )

    data object ArticlesScreen : BottomNavigationItem(
        route = ArticlesFeature.ROUTE_NAME,
        iconResId = R.drawable.articles,
        caption = R.string.articles,
    )

    data object SettingsScreen : BottomNavigationItem(
        route = SettingsFeature.ROUTE_NAME,
        iconResId = R.drawable.settings,
        caption = R.string.settings,
    )
}