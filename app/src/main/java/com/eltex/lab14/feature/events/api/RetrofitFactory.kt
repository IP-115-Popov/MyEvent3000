package com.eltex.lab14.feature.events.api

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object RetrofitFactory {

    private val json = Json {
        ignoreUnknownKeys = true
    }

    val INSTANCE by lazy {
        Retrofit.Builder()
            .client(OkHttpClientFactory.INSTANCE)
            .baseUrl("https://eltex-android.ru/")
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }
}