package dev.progrover.core.base.data.interceptor

import dev.progrover.shmr_finance.core.base.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class BaseInterceptorImpl : BaseInterceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = BuildConfig.TOKEN

        val request = chain.request().newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("Authorization", "Bearer $token")
            .build()

        return chain.proceed(request)
    }
}