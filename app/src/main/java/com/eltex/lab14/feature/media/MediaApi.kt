package com.eltex.lab14.feature.media

import com.eltex.lab14.feature.events.api.RetrofitFactory
import okhttp3.MultipartBody
import retrofit2.create
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface MediaApi {

    @Multipart
    @POST("api/media")
    suspend fun uploadMedia(@Part file: MultipartBody.Part): MediaDto
}