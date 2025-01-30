package com.eltex.lab14.di

import com.eltex.lab14.BuildConfig
import com.eltex.lab14.feature.events.api.EventApi
import com.eltex.lab14.feature.events.api.RetrofitFactory
import com.eltex.lab14.feature.media.MediaApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ApiModule {

    @Singleton
    @Provides
    fun provideOkHttp(): OkHttpClient =
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

    @Singleton
    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit =
        RetrofitFactory.createRetrofit(okHttpClient)

    @Provides
    fun provideMediaApi(retrofit: Retrofit): MediaApi = retrofit.create<MediaApi>()

    @Provides
    fun provideEventApi(retrofit: Retrofit): EventApi = retrofit.create<EventApi>()
}