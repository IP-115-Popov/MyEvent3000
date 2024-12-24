package com.eltex.lab14.api

import com.eltex.lab14.data.Event
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EventApi {
    @GET("api/events")
    fun getAll(): Single<List<Event>>

    @POST("api/events")
    fun savEvent(@Body event: Event): Single<Event>

    @POST("api/events/{id}/likes")
    fun likeById(@Path("id") id: Long): Single<Event>

    @DELETE("api/events/{id}/likes")
    fun deleteLikeById(@Path("id") id: Long): Single<Event>

    @POST("api/events/{id}/participants")
    fun participateById(@Path("id") id: Long): Single<Event>

    @DELETE("api/events/{id}/participants")
    fun deleteParticipateById(@Path("id") id: Long): Single<Event>

    @DELETE("api/events/{id}")
    fun deleteById(@Path("id") id: Long): Completable


    companion object {
        val INSTANCE: EventApi by lazy {
            RetrofitFactory.INSTANCE.create()
        }
    }
}