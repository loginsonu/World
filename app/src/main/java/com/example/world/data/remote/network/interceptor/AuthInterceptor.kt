package com.example.world.data.remote.network.interceptor

import com.example.world.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor():Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("accept","application/json")
            .addHeader("x-rapidapi-key", BuildConfig.SERVER_API_KEY)
            .build()

        return chain.proceed(request)
    }
}