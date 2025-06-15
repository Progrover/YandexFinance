package dev.progrover.articles.impl.data.repository

import dev.progrover.articles.impl.domain.repository.ArticlesRepository
import dev.progrover.core.base.data.repository.BaseRepository
import javax.inject.Inject

class ArticlesRepositoryImpl @Inject constructor(
) : ArticlesRepository, BaseRepository()