package dev.progrover.shmr_finance.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dev.progrover.account.api.di.AccountDependencies
import dev.progrover.account.impl.di.AccountComponent
import dev.progrover.account.impl.di.AccountNavigationModule
import dev.progrover.articles.api.di.ArticlesDependencies
import dev.progrover.articles.impl.di.ArticlesNavigationModule
import dev.progrover.core.base.di.BaseDependencies
import dev.progrover.expenditures.api.di.ExpendituresDependencies
import dev.progrover.expenditures.impl.di.ExpendituresNavigationModule
import dev.progrover.feature.edit.impl.di.EditNavigationModule
import dev.progrover.history.impl.di.HistoryNavigationModule
import dev.progrover.incomes.api.di.IncomesDependencies
import dev.progrover.incomes.impl.di.IncomesNavigationModule
import dev.progrover.settings.impl.di.SettingsNavigationModule
import dev.progrover.shmr_finance.MainApplication
import dev.progrover.shmr_finance.activity.MainActivity
import dev.progrover.shmr_finance.workmanager.StartupWorker
import dev.progrover.shmr_finance.workmanager.SyncWorker
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        BaseDependenciesModule::class,
        WorkerModule::class,
        MainActivityViewModelModule::class,
        ExpendituresDependenciesModule::class,
        ArticlesDependenciesModule::class,
        IncomesDependenciesModule::class,
        AccountDependenciesModule::class,
        SettingsNavigationModule::class,
        AccountNavigationModule::class,
        ArticlesNavigationModule::class,
        IncomesNavigationModule::class,
        ExpendituresNavigationModule::class,
        EditNavigationModule::class,
        HistoryNavigationModule::class,
    ],
)
interface ApplicationComponent {

    fun inject(activity: MainActivity)

    fun getAccountComponent(): AccountComponent

    fun syncWorkerFactory(): SyncWorker.Factory

    fun startupWorkerFactory(): StartupWorker.Factory

    fun inject(application: MainApplication)

    fun getAccountDependencies(): AccountDependencies

    fun getArticlesDependencies(): ArticlesDependencies

    fun getExpendituresDependencies(): ExpendituresDependencies

    fun getIncomesDependencies(): IncomesDependencies


    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
            @BindsInstance baseDependencies: BaseDependencies
        ): ApplicationComponent
    }

    fun getMainViewModelFactory(): ViewModelFactory
}