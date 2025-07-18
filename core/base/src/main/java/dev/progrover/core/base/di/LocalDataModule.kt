package dev.progrover.core.base.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dev.progrover.core.base.data.local.AccountDao
import dev.progrover.core.base.data.local.AppDatabase
import dev.progrover.core.base.data.local.ArticleDao
import dev.progrover.core.base.data.local.TransactionDao
import dev.progrover.core.base.data.local.mapper.AccountEntityMapper
import dev.progrover.core.base.data.local.mapper.CategoryEntityMapper
import dev.progrover.core.base.data.local.mapper.TransactionEntityMapper
import dev.progrover.core.base.data.local.provider.LocalAccountProvider
import dev.progrover.core.base.data.local.provider.LocalAccountProviderImpl
import dev.progrover.core.base.data.local.provider.LocalArticleProvider
import dev.progrover.core.base.data.local.provider.LocalArticleProviderImpl
import dev.progrover.core.base.data.local.provider.LocalTransactionProvider
import dev.progrover.core.base.data.local.provider.LocalTransactionProviderImpl
import javax.inject.Singleton

@Module
class LocalDataModule {

    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "YandexFinanceStorage.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideTransactionDao(appDatabase: AppDatabase): TransactionDao =
        appDatabase.transactionDao()

    @Provides
    @Singleton
    fun provideCategoryDao(appDatabase: AppDatabase): ArticleDao =
        appDatabase.categoryDao()

    @Provides
    @Singleton
    fun provideAccountDao(appDatabase: AppDatabase): AccountDao =
        appDatabase.accountDao()

    @Provides
    @Singleton
    fun provideLocalAccountProvider(
        accountDao: AccountDao,
        accountEntityMapper: AccountEntityMapper
    ): LocalAccountProvider =
        LocalAccountProviderImpl(
            accountDao = accountDao,
            accountEntityMapper = accountEntityMapper
        )

    @Provides
    @Singleton
    fun provideLocalArticleProvider(
        articleDao: ArticleDao,
        articleEntityMapper: CategoryEntityMapper
    ): LocalArticleProvider =
        LocalArticleProviderImpl(
            categoryDao = articleDao,
            categoryEntityMapper = articleEntityMapper
        )

    @Provides
    @Singleton
    fun provideLocalTransactionProvider(
        accountDao: AccountDao,
        accountEntityMapper: AccountEntityMapper,
        transactionDao: TransactionDao,
        articleDao: ArticleDao,
        categoryEntityMapper: CategoryEntityMapper,
        transactionEntityMapper: TransactionEntityMapper,
    ): LocalTransactionProvider =
        LocalTransactionProviderImpl(
            accountDao = accountDao,
            accountEntityMapper = accountEntityMapper,
            transactionDao = transactionDao,
            articleDao = articleDao,
            transactionEntityMapper = transactionEntityMapper,
            categoryEntityMapper = categoryEntityMapper
        )
}
