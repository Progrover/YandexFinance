package dev.progrover.settings.impl.presentation.viewmodel

import dev.progrover.core.base.data.storage.Prefs
import dev.progrover.core.base.presentation.viewmodel.BaseViewModel
import dev.progrover.core.base.utils.SYNC_TIME_HOURS
import dev.progrover.settings.impl.presentation.contract.sync.SyncUIEffect
import dev.progrover.settings.impl.presentation.contract.sync.SyncUIEvent
import dev.progrover.settings.impl.presentation.contract.sync.SyncUIState
import javax.inject.Inject

class SyncViewModel @Inject constructor(
    private val prefs: Prefs
) :
    BaseViewModel<SyncUIEvent, SyncUIState, SyncUIEffect>(
        SyncUIState(
            prefs.getInt(SYNC_TIME_HOURS, 4).toFloat()
        )
    ) {
    override fun handleUIEvent(event: SyncUIEvent) {
        when (event) {
            SyncUIEvent.OnBackClick -> {
                prefs.putInt(SYNC_TIME_HOURS, currentState.time.toInt())
                setEffect(SyncUIEffect.NavigateBack)
            }

            is SyncUIEvent.OnTimeChange ->
                setState(currentState.copy(time = event.newTime))
        }
    }
}