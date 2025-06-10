package dev.progrover.articles.impl.data.repository

import dev.progrover.core.base.data.repository.BaseRepository
import dev.progrover.articles.impl.domain.repository.ArticlesRepository
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class ArticlesRepositoryImpl @Inject constructor(
) : ArticlesRepository, BaseRepository(Dispatchers.IO)