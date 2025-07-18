package dev.progrover.settings.impl.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import dev.progrover.core.base.di.BaseComponentProvider
import dev.progrover.settings.impl.di.DaggerSettingsComponent
import dev.progrover.settings.impl.di.SettingsComponent

@Composable
internal fun SettingsComponent(): SettingsComponent {
    val context = LocalContext.current.applicationContext
    val baseDependencies = remember {
        (context as BaseComponentProvider).getBaseComponent()
    }
    val component = remember(baseDependencies) {
        DaggerSettingsComponent.builder().baseDependencies(baseDependencies).build()
    }
    return component
}