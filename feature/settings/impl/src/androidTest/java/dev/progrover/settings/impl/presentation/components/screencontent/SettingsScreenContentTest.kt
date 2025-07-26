package dev.progrover.settings.impl.presentation.components.screencontent

import androidx.activity.ComponentActivity
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.google.common.truth.Truth
import dev.progrover.settings.impl.presentation.contract.settings.SettingsUIEvent
import dev.progrover.settings.impl.presentation.contract.settings.SettingsUIState
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

class TestActivity : ComponentActivity()

@RunWith(JUnit4::class)
class SettingsScreenContentTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<TestActivity>()

    @Test
    fun themeSwitch_triggersOnEventWithCorrectValue() {
        var latestThemeToggle: Boolean? = null

        composeTestRule.setContent {
            SettingsScreenContent(
                modifier = Modifier,
                uiState = SettingsUIState(themeModeOn = false),
                onEvent = {
                    if (it is SettingsUIEvent.OnThemeClick) {
                        latestThemeToggle = it.newStatus
                    }
                },
                snackbarHostState = SnackbarHostState()
            )
        }

        composeTestRule
            .onNodeWithTag("themeSwitchTag")
            .assertIsOff()

        composeTestRule
            .onNodeWithTag("themeSwitchTag")
            .performClick()

        Truth.assertThat(latestThemeToggle).isTrue()
    }
}
