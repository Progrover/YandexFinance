
import android.util.Log
import timber.log.Timber

class TimberReleaseTree : Timber.Tree() {

    /**
     * для использования не фатальных ошибок для крашлитики использовать Timber.e или Timber.w как Timber.e(throwable, mwssage)
     */
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        when (priority) {
            Log.ERROR, Log.WARN -> sendToCrashlytics(priority, tag, message, t)
        }
    }

    private fun sendToCrashlytics(
        priority: Int,
        tag: String?,
        message: String,
        throwable: Throwable?,
    ) {
        //TODO install crashlytics and send errors
    }
}
