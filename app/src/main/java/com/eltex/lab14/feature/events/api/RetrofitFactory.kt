package com.eltex.lab14.feature.events.api

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object RetrofitFactory {

    private val json = Json {
        ignoreUnknownKeys = true
    }

    fun createRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("https://eltex-android.ru/")
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
}