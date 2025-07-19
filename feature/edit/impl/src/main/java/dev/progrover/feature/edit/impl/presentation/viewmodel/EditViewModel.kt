package dev.progrover.feature.edit.impl.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dev.progrover.account.api.domain.AccountPropertiesProvider
import dev.progrover.articles.api.domain.interactor.ArticlesInteractor
import dev.progrover.core.base.model.Alert
import dev.progrover.core.base.model.LocalStorageError
import dev.progrover.core.base.model.TransactionsUpdater
import dev.progrover.core.base.navigation.RouteDesc
import dev.progrover.core.base.presentation.viewmodel.BaseViewModel
import dev.progrover.core.base.utils.combineDateAndTime
import dev.progrover.core.base.utils.extractTimeFromIsoString
import dev.progrover.core.base.utils.formatInputAsTime
import dev.progrover.core.base.utils.inputTimeCorrect
import dev.progrover.feature.edit.api.model.EditVatiant
import dev.progrover.feature.edit.impl.domain.model.EditAlert
import dev.progrover.feature.edit.impl.domain.model.EditTransaction
import dev.progrover.feature.edit.impl.domain.repository.EditRepository
import dev.progrover.feature.edit.impl.presentation.contract.edit.EditUIEffect
import dev.progrover.feature.edit.impl.presentation.contract.edit.EditUIEvent
import dev.progrover.feature.edit.impl.presentation.contract.edit.EditUIState
import dev.progrover.feature.edit.impl.presentation.navigation.EditNavigationFactory.Companion.ARG_KEY_ID
import dev.progrover.feature.edit.impl.presentation.navigation.EditNavigationFactory.Companion.ARG_KEY_TYPE_ACTION
import dev.progrover.feature.edit.impl.presentation.navigation.EditNavigationFactory.Companion.ARG_KEY_TYPE_TRANSACTION
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * ViewModel, привязанная к edit feature
 */
class EditViewModel @AssistedInject constructor(
    private val editRepository: EditRepository,
    private val idProvider: AccountPropertiesProvider,
    private val transactionsUpdater: TransactionsUpdater,
    private val articlesInteractor: ArticlesInteractor,
    @Assisted savedStateHandle: SavedStateHandle,
) :
    BaseViewModel<EditUIEvent, EditUIState, EditUIEffect>(
        EditUIState(
            actionType = savedStateHandle[ARG_KEY_TYPE_ACTION]!!,
            transactionType = savedStateHandle[ARG_KEY_TYPE_TRANSACTION]!!,
        )
    ) {

    private val transactionId: Int = savedStateHandle[ARG_KEY_ID]!!

    init {
        setState(currentState.copy(isLoading = true))
        getArticles()
        getTransaction()
    }

    override fun handleUIEvent(event: EditUIEvent) =
        when (event) {
            EditUIEvent.OnAlertDialogDone ->
                setState(currentState.copy(alert = null))

            is EditUIEvent.OnAmountChange ->
                setState(
                    currentState.copy(
                        transaction = currentState.transaction!!.copy(
                            amount = event.newAmount
                        )
                    )
                )

            EditUIEvent.OnCategoryClick ->
                setState(
                    currentState.copy(
                        showCategoryList = true
                    )
                )

            is EditUIEvent.OnCommentChange ->
                setState(
                    currentState.copy(
                        transaction = currentState.transaction!!
                            .copy(comment = event.newComment)
                    )
                )

            EditUIEvent.OnCrossClick ->
                setEffect(EditUIEffect.NavigateBack)

            is EditUIEvent.OnDateChange ->
                setState(
                    currentState.copy(
                        showDatePicker = false,
                        transaction = currentState.transaction!!
                            .copy(dateTime = event.newDate)
                    )
                )

            EditUIEvent.OnDatePickerClose ->
                setState(
                    currentState.copy(
                        showDatePicker = false,
                    )
                )

            EditUIEvent.OnDeleteClick ->
                onDelete()

            EditUIEvent.OnTickClick ->
                handleOnTickClick()

            is EditUIEvent.OnTimeChange -> {
                if (event.newTime.length <= 4)
                    setState(
                        currentState
                            .copy(
                                transactionTime =
                                    event.newTime
                            )
                    ) else Unit
            }

            is EditUIEvent.OnNewCategory ->
                setState(
                    currentState.copy(
                        showCategoryList = false,
                        transaction = currentState.transaction!!
                            .copy(categoryId = event.newCategoryId)
                    )
                )

            EditUIEvent.OnDateClick ->
                setState(currentState.copy(showDatePicker = true))

            EditUIEvent.OnCategoriesListClose ->
                setState(
                    currentState.copy(
                        showCategoryList = false
                    )
                )
        }

    private fun onDelete() {
        tryMultipleLoad(
            function = { editRepository.deleteTransaction(currentState.transaction?.id!!) },
            onSuccess = {
                viewModelScope.launch {
                    setState(currentState.copy(alert = EditAlert.DeleteSuccess))
                    transactionsUpdater.updateTransactions(currentState.transactionType)
                    delay(2000)
                    setEffect(EditUIEffect.NavigateBack)
                }
            },
            onFailure = { throwable ->
                setState(currentState.copy(alert = throwable as Alert))
            }
        )
    }

    private fun getTransaction() {
        idProvider.getId(viewModelScope) { result ->
            result.fold(
                onSuccess = { accountId ->
                    when (currentState.actionType) {
                        EditVatiant.Add ->
                            setState(
                                currentState.copy(
                                    isLoading = false,
                                    currency = idProvider.getCurrency(),
                                    transaction = EditTransaction(
                                        accountId = accountId,
                                        accountName = idProvider.getName(),
                                        categoryId = -1,
                                        comment = "",
                                        amount = "",
                                        dateTime = -1L
                                    )
                                )
                            )

                        EditVatiant.Edit -> {
                            tryMultipleLoad(
                                function = {
                                    editRepository.getEditTransaction(
                                        accountId,
                                        transactionId
                                    )
                                },
                                onSuccess = { result ->
                                    setState(
                                        currentState.copy(
                                            isLoading = false,
                                            transactionTime = result.dateTime.extractTimeFromIsoString(),
                                            transaction = result,
                                            currency = idProvider.getCurrency()
                                        )
                                    )
                                },
                                onFailure = { throwable ->
                                    tryMultipleLoad(
                                        function = {
                                            editRepository.getEditTransactionFromLocalStorage(
                                                accountId,
                                                transactionId
                                            )
                                        },
                                        onSuccess = { result ->
                                            setState(
                                                currentState.copy(
                                                    isLoading = false,
                                                    transactionTime = result.dateTime.extractTimeFromIsoString(),
                                                    transaction = result,
                                                    currency = idProvider.getCurrency()
                                                )
                                            )
                                        },
                                        onFailure = {
                                            setState(
                                                currentState.copy(
                                                    alert = LocalStorageError.LocalError
                                                )
                                            )
                                        }
                                    )
                                    setState(
                                        currentState.copy(
                                            alert = throwable as Alert
                                        )
                                    )
                                }
                            )
                        }
                    }
                },
                onFailure = {
                    setState(
                        currentState.copy(
                            alert = it as Alert
                        )
                    )
                }
            )
        }
    }

    private fun getArticles() {
        if (currentState.categories.isEmpty()) {
            tryMultipleLoad(
                function = {
                    articlesInteractor.getCategoriesByType(
                        when (currentState.transactionType) {
                            RouteDesc.Incomes -> true
                            RouteDesc.Expenditures -> false
                        }
                    )
                },
                onSuccess = { result ->
                    setState(
                        currentState.copy(
                            categories = result
                        )
                    )
                },
                onFailure = {
                    tryMultipleLoad(
                        function = {
                            articlesInteractor.getCategoriesByTypeFromLocalStorage(
                                when (currentState.transactionType) {
                                    RouteDesc.Incomes -> true
                                    RouteDesc.Expenditures -> false
                                }
                            )
                        },
                        onSuccess = { result ->
                            setState(
                                currentState.copy(
                                    categories = result
                                )
                            )
                        },
                        onFailure = {
                            setState(currentState.copy(alert = LocalStorageError.LocalError))
                        }
                    )
                }
            )

        }
    }

    private fun handleOnTickClick() {
        if (currentState.transactionTime.inputTimeCorrect()) {
            if (currentState.transaction!!.categoryId != -1) {
                val commonDate = combineDateAndTime(
                    currentState.transaction!!.dateTime,
                    currentState.transactionTime.formatInputAsTime()
                )
                setState(
                    currentState.copy(
                        transaction = currentState.transaction!!.copy(
                            dateTime = commonDate
                        )
                    )
                )

                var synced = true
                tryMultipleLoad(
                    function = {
                        when (currentState.actionType) {
                            EditVatiant.Add -> editRepository.addTransaction(currentState.transaction!!)
                            EditVatiant.Edit -> editRepository.updateTransactionInfo(
                                currentState.transaction!!
                            )
                        }
                    },
                    onSuccess = {
                        viewModelScope.launch {
                            setState(
                                currentState.copy(
                                    alert = when (currentState.actionType) {
                                        EditVatiant.Add ->
                                            EditAlert.AddSuccess

                                        EditVatiant.Edit ->
                                            EditAlert.UpdateSuccess
                                    }
                                )
                            )
                            transactionsUpdater.updateTransactions(currentState.transactionType)
                            delay(2000)
                            setEffect(EditUIEffect.NavigateBack)
                        }
                    },
                    onFailure = { throwable ->
                        synced = false
                        setState(currentState.copy(alert = throwable as Alert))
                    }
                )
                tryMultipleLoad(
                    function = {
                        when (currentState.actionType) {
                            EditVatiant.Add -> editRepository.addTransactionToLocalStorage(
                                currentState.transaction!!.copy(id = (100_000_000..999_999_999).random()),
                                synced
                            )

                            EditVatiant.Edit -> editRepository.updateTransactionInfoInLocalStorage(
                                currentState.transaction!!,
                                synced
                            )
                        }
                    },
                    onSuccess = {
                        viewModelScope.launch {
                            setState(
                                currentState.copy(
                                    alert = when (currentState.actionType) {
                                        EditVatiant.Add ->
                                            EditAlert.AddSuccess

                                        EditVatiant.Edit ->
                                            EditAlert.UpdateSuccess
                                    }
                                )
                            )
                            transactionsUpdater.updateTransactions(currentState.transactionType)
                            delay(2000)
                            setEffect(EditUIEffect.NavigateBack)
                        }
                    },
                    onFailure = {
                        setState(currentState.copy(alert = LocalStorageError.LocalError))
                    }
                )
            } else {
                setState(currentState.copy(alert = EditAlert.IncorrectDataError))
            }
        } else {
            setState(currentState.copy(alert = EditAlert.TimeError))
        }
    }
}