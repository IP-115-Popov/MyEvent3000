package com.eltex.lab14.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.time.ZoneId

@Module
@InstallIn(SingletonComponent::class)
class DataTimeFormatterModule {
    @Provides
    fun providesZoneId(): ZoneId = ZoneId.systemDefault()
}