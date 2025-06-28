package dev.progrover.articles.impl.presentation.components.screencontent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import dev.progrover.articles.impl.presentation.contract.articles.ArticlesUIEvent
import dev.progrover.articles.impl.presentation.contract.articles.ArticlesUIState
import dev.progrover.core.theme.AppTheme
import dev.progrover.core.uicommon.utils.bottomNavigationPadding
import dev.progrover.core.uicommon.utils.conditionally
import dev.progrover.core.uicommon.utils.noRippleClickable
import dev.progrover.core.uicommon.views.BasicColumn
import dev.progrover.core.uicommon.views.CustomAlertDialog
import dev.progrover.core.uicommon.views.DefaultListItem
import dev.progrover.core.uicommon.views.DefaultTextField
import dev.progrover.core.uicommon.views.DefaultToolbar
import dev.progrover.core.uicommon.views.ProgressIndicator
import dev.progrover.shmr_finance.feature.articles.impl.R

@Composable
internal fun ArticlesScreenContent(
    modifier: Modifier,
    uiState: ArticlesUIState,
    onEvent: (ArticlesUIEvent) -> Unit,
    snackbarHostState: SnackbarHostState,
) {

    val scrollState = rememberScrollState()
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.surface)
            .bottomNavigationPadding()
    ) {
        BasicColumn(
            modifier = modifier
                .fillMaxSize()
                .noRippleClickable {
                    focusManager.clearFocus()
                }
                .conditionally(
                    condition = !uiState.isLoading,
                    trueExtension = {
                        verticalScroll(scrollState)
                    }
                ),
            toolbar = {
                DefaultToolbar(
                    modifier = Modifier,
                    title = stringResource(R.string.articles_title),
                )

                DefaultTextField(
                    text = uiState.searchText,
                    hintResId = R.string.find_article,
                    endIconId = R.drawable.find,
                    focusRequester = focusRequester,
                    onTextChange = { newText ->
                        onEvent(ArticlesUIEvent.OnSearchTextChange(newText))
                    }
                )
            },
        ) {
            if (!uiState.isLoading) {
                uiState.articlesForPresentation.forEach { article ->
                    DefaultListItem(
                        modifier = Modifier,
                        title = article.name,
                        verticalTextPadding = AppTheme.paddings.padding16,
                        startIcon = article.emoji,
                        onClick = { onEvent(ArticlesUIEvent.OnArticleClick(article.id)) }
                    )
                }
            } else {
                ProgressIndicator()
            }
        }

        SnackbarHost(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(AppTheme.paddings.padding16),
            hostState = snackbarHostState,
        )

        if (uiState.error != null) {
            CustomAlertDialog(
                modifier = Modifier,
                error = uiState.error,
                onDismiss = { onEvent(ArticlesUIEvent.OnErrorDialogDone) }
            )
        }
    }
}