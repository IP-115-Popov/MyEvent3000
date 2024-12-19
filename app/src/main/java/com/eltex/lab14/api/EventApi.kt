package com.eltex.lab14.api

import com.eltex.lab14.data.Event
import retrofit2.Call
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EventApi {
    @GET("api/events")
    fun getAll(): Call<List<Event>>

    @POST("api/events")
    fun savEvent(@Body event: Event): Call<Event>

    @POST("api/events/{id}/likes")
    fun likeById(@Path("id") id: Long) : Call<Event>

    @DELETE("api/events/{id}/likes")
    fun deleteLikeById(@Path("id") id: Long) : Call<Event>


    companion object {
        val INSTANCE: EventApi by lazy {
            RetrofitFactory.INSTANCE.create()
        }
    }
}