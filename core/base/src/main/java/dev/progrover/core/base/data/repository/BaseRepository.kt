package dev.progrover.core.base.data.repository

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dev.progrover.core.base.di.CoroutineQualifiers
import dev.progrover.shmr_finance.core.base.BuildConfig
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.withContext
import javax.inject.Inject

open class BaseRepository @Inject constructor(
    @CoroutineQualifiers.IoDispatcher
    private val dispatcher: CoroutineDispatcher,
    @CoroutineQualifiers.DefaultCoroutineExceptionHandler
    private val coroutineExceptionHandler: CoroutineExceptionHandler,
) {

    val moshi: Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    protected suspend fun <T> executeOnIO(
        call: suspend () -> T,
    ): T =
        withContext(dispatcher + coroutineExceptionHandler) {
            call.invoke()
        }

    protected val tokenAvaliable = BuildConfig.TOKEN.isNotBlank()
}