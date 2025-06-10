pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
                includeGroup("org.jetbrains.kotlin")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "YandexFinance"
include(":app")
include(":core")
include(":core:base")
include(":core:theme")
include(":core:uicommon")
include(":feature")
include(":feature:incomes:api")
include(":feature:incomes:impl")
include(":feature:expenditures:api")
include(":feature:expenditures:impl")
include(":feature:settings:api")
include(":feature:settings:impl")
include(":feature:articles:api")
include(":feature:articles:impl")
include(":feature:account:api")
include(":feature:account:impl")
