package dev.progrover.articles.impl.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dev.progrover.articles.impl.presentation.viewmodel.ArticlesViewModel

@Module
internal interface ArticlesViewModelModule {

    @[Binds IntoMap ViewModelKey(ArticlesViewModel::class)]
    fun bindArticlesViewModel(viewModel: ArticlesViewModel): ViewModel
}