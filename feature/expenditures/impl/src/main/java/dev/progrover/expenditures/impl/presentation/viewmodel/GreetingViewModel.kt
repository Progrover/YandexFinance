package dev.progrover.expenditures.impl.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.progrover.core.base.data.storage.Prefs
import dev.progrover.core.base.di.CoroutineQualifiers
import dev.progrover.core.base.presentation.viewmodel.BaseViewModel
import dev.progrover.expenditures.impl.domain.interactor.ExpendituresInteractor
import dev.progrover.expenditures.impl.presentation.contract.greeting.GreetingUIEffect
import dev.progrover.expenditures.impl.presentation.contract.greeting.GreetingUIEvent
import dev.progrover.expenditures.impl.presentation.contract.greeting.GreetingUIState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GreetingViewModel @Inject constructor(
    @CoroutineQualifiers.DefaultCoroutineExceptionHandler
    private val coroutineExceptionHandler: CoroutineExceptionHandler,
    @CoroutineQualifiers.IoDispatcher
    private val ioDispatcher: CoroutineDispatcher,
    private val sharedPrefs: Prefs,
    private val expendituresInteractor: ExpendituresInteractor,
    @ApplicationContext
    private val context: Context,
) : BaseViewModel<GreetingUIEvent, GreetingUIState, GreetingUIEffect>(GreetingUIState()) {

    init {
        runProgress()
    }

    override fun handleUIEvent(event: GreetingUIEvent) = Unit

    private fun runProgress() {
        viewModelScope.launch(coroutineExceptionHandler) {
            /**
             * ждем окончания анимации логотипа
             */
            delay(500)
            setState(currentState.copy(isLogoVisible = true))
            delay(2000)
            setState(currentState.copy(isTextVisible = true))
            /** ждем окончания анимации текста логотипа,
             * позже прикрутим проверку авторизации юзера, если потребуется
             */
            delay(2800)
            setEffect(GreetingUIEffect.NavigateToExpendituresScreen)
        }
    }
}