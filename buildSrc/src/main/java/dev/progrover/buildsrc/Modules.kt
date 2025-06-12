object Core {
    private const val core = ":core"
    const val base = "$core:base"
    const val theme = "$core:theme"
    const val uicommon = "$core:uicommon"
}

object Feature {
    private const val feature = ":feature"

    object Incomes {
        private const val incomes = "$feature:incomes"
        const val api = "$incomes:api"
        const val impl = "$incomes:impl"
    }

    object Expenditures {
        private const val expenditures = "$feature:expenditures"
        const val api = "$expenditures:api"
        const val impl = "$expenditures:impl"
    }

    object Articles {
        private const val articles = "$feature:articles"
        const val api = "$articles:api"
        const val impl = "$articles:impl"
    }

    object Account {
        private const val account = "$feature:account"
        const val api = "$account:api"
        const val impl = "$account:impl"
    }

    object Settings {
        private const val settings = "$feature:settings"
        const val api = "$settings:api"
        const val impl = "$settings:impl"
    }
}