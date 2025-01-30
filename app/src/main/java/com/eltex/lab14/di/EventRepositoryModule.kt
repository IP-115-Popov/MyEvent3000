package com.eltex.lab14.di

import com.eltex.lab14.feature.events.repository.EventRepository
import com.eltex.lab14.feature.events.repository.NetworkEventsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface EventRepositoryModule {

    @Binds
    fun bindNetworkEventRepository(impl: NetworkEventsRepository): EventRepository
}