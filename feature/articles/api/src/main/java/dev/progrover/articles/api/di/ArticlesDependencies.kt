package dev.progrover.articles.api.di

import dev.progrover.articles.api.domain.interactor.ArticlesInteractor

interface ArticlesDependencies {
    fun articlesInteractor(): ArticlesInteractor
}