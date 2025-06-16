package dev.progrover.core.base.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    /**
     * Функция, перезапускающая запрос трижды с интервалом в 2 секунды
     */
    protected fun <T> tryMultipleLoad(
        triesCount: Int = 3,
        function: suspend () -> Result<T>,
        onSuccess: (T) -> Unit,
        onFailure: (String?) -> Unit,
    ) {
        viewModelScope.launch {
            for (tryNumber in 1..triesCount) {
                function().onSuccess { result ->
                    onSuccess(result)
                    return@launch
                }.onFailure { exception: Throwable ->
                    if (tryNumber == 1) {onFailure(exception.message)}
                }
                delay(2000)
            }
        }
    }
}