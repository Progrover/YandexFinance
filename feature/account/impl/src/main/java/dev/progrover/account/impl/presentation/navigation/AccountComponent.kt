package dev.progrover.account.impl.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import dev.progrover.account.impl.di.AccountComponent
import dev.progrover.account.impl.di.AccountComponentProvider
import dev.progrover.account.impl.di.DaggerAccountComponent
import dev.progrover.core.base.di.BaseComponentProvider

@Composable
internal fun AccountComponent(): AccountComponent {
    val context = LocalContext.current.applicationContext
    val component = remember {
        (context as AccountComponentProvider).getAccountComponent()
    }
    return component
}