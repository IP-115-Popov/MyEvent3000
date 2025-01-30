package com.eltex.lab14.feature.events.api

import com.eltex.lab14.feature.events.data.Event
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface EventApi {
    @GET("api/events/{id}/before")
    suspend fun getBefore(@Path("id") id: Long, @Query("count") count: Int): List<Event>

    @GET("api/events/latest")
    suspend fun getLatest(@Query("count") count: Int): List<Event>

    @GET("api/events")
    suspend fun getAll(): List<Event>

    @POST("api/events")
    suspend fun savEvent(@Body event: Event): Event

    @POST("api/events/{id}/likes")
    suspend fun likeById(@Path("id") id: Long): Event

    @DELETE("api/events/{id}/likes")
    suspend fun deleteLikeById(@Path("id") id: Long): Event

    @POST("api/events/{id}/participants")
    suspend fun participateById(@Path("id") id: Long): Event

    @DELETE("api/events/{id}/participants")
    suspend fun deleteParticipateById(@Path("id") id: Long): Event

    @DELETE("api/events/{id}")
    suspend fun deleteById(@Path("id") id: Long)

}