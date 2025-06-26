package dev.progrover.core.base.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.progrover.core.base.model.ApiResponse
import dev.progrover.core.base.model.ServerError
import dev.progrover.core.base.presentation.mvi.UIEffect
import dev.progrover.core.base.presentation.mvi.UIEvent
import dev.progrover.core.base.presentation.mvi.UIState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
/**
 * Абстрактный класс ViewModel, от которого наследуются все ViewModel
 */
abstract class BaseViewModel<Event : UIEvent, State : UIState, Effect : UIEffect>
    (initialState: State) : ViewModel() {

    val currentState: State
        get() = uiState.value

    private val _uiState: MutableStateFlow<State> = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    private val event: MutableSharedFlow<Event> = MutableSharedFlow()

    private val _effect: Channel<Effect> = Channel()
    val effect = _effect.receiveAsFlow()

    protected fun setState(reduce: State.() -> State) {
        val newState = currentState.reduce()
        _uiState.value = newState
    }

    private fun subscribeEvents() {
        viewModelScope.launch {
            event.collect(::handleUIEvent)
        }
    }

    protected fun setState(newState: State) =
        setState { newState }

    fun setEvent(newEvent: Event) {
        viewModelScope.launch {
            event.emit(newEvent)
        }
    }

    protected fun setEffect(builder: () -> Effect) {
        val effectValue = builder()
        viewModelScope.launch {
            _effect.send(effectValue)
        }
    }

    protected fun setEffect(effect: Effect) =
        setEffect { effect }

    protected abstract fun handleUIEvent(event: Event)

    init {
        subscribeEvents()
    }

     // Функция, перезапускающая запрос трижды с интервалом в 2 секунды при code 500
    protected fun <T> tryMultipleLoad(
        triesCount: Int = 4,
        function: suspend () -> ApiResponse<T>,
        onSuccess: (T) -> Unit,
        onFailure: (ServerError) -> Unit,
    ) {
        viewModelScope.launch {
            for (tryNumber in 1..triesCount) {
                val result = function()
                if (result.value != null) {
                    onSuccess(result.value)
                    return@launch
                } else {
                    when (result.code) {
                        0 -> {
                            onFailure(result.error)
                            return@launch
                        }

                        400 -> {
                            onFailure(ServerError.Error_400)
                            return@launch
                        }

                        401 -> {
                            onFailure(ServerError.Error_401)
                            return@launch
                        }

                        404 -> {
                            onFailure(ServerError.Error_404)
                            return@launch
                        }

                        409 -> {
                            onFailure(ServerError.Error_409)
                            return@launch
                        }

                        429 -> {
                            if (tryNumber == 1) onFailure(ServerError.Error_429)
                            if (tryNumber == 4) onFailure(ServerError.MultipleLoadsError)
                        }

                        500 -> {
                            if (tryNumber == 1) onFailure(ServerError.Error_500)
                            if (tryNumber == 4) onFailure(ServerError.MultipleLoadsError)
                        }

                        else -> {
                            onFailure(ServerError.UnknownError)
                            return@launch
                        }
                    }
                }
                delay(2000)
            }
        }
    }
}