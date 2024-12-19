package com.eltex.lab14.api

import com.eltex.lab14.data.Event
import retrofit2.Call
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface EventApi {
    @GET("api/events")
    fun getAll(): Call<List<Event>>

    @POST("api/events")
    fun savEvent(@Body event: Event): Call<Event>

    companion object {
        val INSTANCE: EventApi by lazy {
            RetrofitFactory.INSTANCE.create()
        }
    }
}