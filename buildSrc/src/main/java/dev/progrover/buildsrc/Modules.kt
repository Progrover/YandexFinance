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
}