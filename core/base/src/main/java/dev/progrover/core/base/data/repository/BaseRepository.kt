package dev.progrover.core.base.data.repository

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dev.progrover.core.base.di.CoroutineQualifiers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

open class BaseRepository(
    @CoroutineQualifiers.IoDispatcher
    private val dispatcher: CoroutineDispatcher,
) {

    val moshi: Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    protected suspend fun <T> executeOnIO(
        call: suspend () -> T,
    ): T =
        withContext(dispatcher) {
            call.invoke()
        }
}