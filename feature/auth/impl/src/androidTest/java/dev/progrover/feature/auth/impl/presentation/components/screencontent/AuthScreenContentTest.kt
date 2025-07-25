package dev.progrover.feature.auth.impl.presentation.components.screencontent

import androidx.activity.ComponentActivity
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import dev.progrover.feature.auth.api.domain.model.NavigationVariant
import dev.progrover.feature.auth.impl.presentation.contract.auth.AuthUIEvent
import dev.progrover.feature.auth.impl.presentation.contract.auth.AuthUIState
import org.junit.Rule
import org.junit.Test

class TestActivity : ComponentActivity()

/**
 * Тест необходим для проверки работоспособности поля ввода пин-кода
 */
class AuthScreenContentTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<TestActivity>()

    private var lastPin: Int? = null

    @Test
    fun testPinCodeInput_whenStartNavigationVariant() {

        val uiState = AuthUIState(
            task = NavigationVariant.Start,
            pinCode = null,
            secondPinCode = null,
            secondStep = false,
            pinCodeIsCorrect = true,
            alert = null
        )

        composeTestRule.setContent {
            AuthScreenContent(
                modifier = Modifier,
                uiState = uiState,
                onEvent = { event ->
                    if (event is AuthUIEvent.OnFirstPinCodeChange) {
                        lastPin = event.newPinCode
                    }
                }
            )
        }

        composeTestRule.onNodeWithTag("PinCodeInput")
            .performClick()

        composeTestRule.waitForIdle()

        composeTestRule.onNode(hasSetTextAction())
            .performTextInput("1234")

        composeTestRule.waitForIdle()

        assert(lastPin == 1234)
    }
}
