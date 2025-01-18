package com.eltex.lab14.feature.events.api

import com.eltex.lab14.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object OkHttpClientFactory {
    val INSTANCE by lazy {
        OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).let {
            if (BuildConfig.DEBUG) {
                it.addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            } else {
                it
            }
        }.addInterceptor { chain ->
            chain.proceed(
                chain.request().newBuilder().addHeader("Api-Key", BuildConfig.API_KEY)
                    .addHeader("Authorization", BuildConfig.AUTHORIZATION).build()
            )
        }.build()
    }
}