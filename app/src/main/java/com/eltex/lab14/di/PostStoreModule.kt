package com.eltex.lab14.di

import com.eltex.lab14.feature.events.effecthandler.EventEffectHandler
import com.eltex.lab14.feature.events.reducer.EventReducer
import com.eltex.lab14.feature.events.repository.EventRepository
import com.eltex.lab14.feature.events.ui.EventUiModelMapper
import com.eltex.lab14.feature.events.viewmodel.EventMessage
import com.eltex.lab14.feature.events.viewmodel.EventStore
import com.eltex.lab14.feature.events.viewmodel.EventUiState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class PostStoreModule {
    @Provides
    fun provideEventStore(
        reducer: EventReducer,
        effectHandler: EventEffectHandler
    ): EventStore = EventStore(
        reducer, effectHandler, setOf(EventMessage.Refresh), EventUiState()
    )

}