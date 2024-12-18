package com.eltex.lab14.repository

import com.eltex.lab14.BuildConfig
import com.eltex.lab14.data.Event
import com.eltex.lab14.util.Callback
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.util.concurrent.TimeUnit


class NetworkEventsRepository() : EventRepository {

    private val json = Json {
        ignoreUnknownKeys = true
    }

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .let {
            if (BuildConfig.DEBUG) {
                it.addInterceptor(
                    HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }
                )
            } else {
                it
            }
        }
        .build()

    override fun getEvent(callback: Callback<List<Event>>) {
        val call = client.newCall(
            Request.Builder()
                .url("https://eltex-android.ru/api/posts")
                .addHeader("Api-Key", BuildConfig.API_KEY)
                .build()
        )

       call.enqueue(
            object : okhttp3.Callback {
                override fun onFailure(call: Call, e: IOException) {
                    callback.onError(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        try {
                            callback.onSuccess(
                                json.decodeFromString(requireNotNull(response.body).string())
                            )
                        } catch (e: Exception) {
                            callback.onError(e)
                        }

                    } else {
                        callback.onError(RuntimeException("Response code is ${response.code}"))

                    }
                }
            }
        )
    }

    override fun likeById(id: Long, callback: Callback<Event>) {
        TODO("Not yet implemented")
    }

    override fun participateById(id: Long, callback: Callback<Event>) {
        TODO("Not yet implemented")
    }

    override fun save(id: Long, content: String, callback: Callback<Event>) {
        TODO("Not yet implemented")
    }

    override fun deleteById(id: Long, callback: Callback<Unit>) {
        TODO("Not yet implemented")
    }
}