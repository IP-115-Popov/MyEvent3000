//package com.eltex.lab14.feature.events
//
//import android.content.Context
//import com.eltex.lab14.feature.events.effecthandler.EventEffectHandler
//import com.eltex.lab14.feature.events.reducer.EventReducer
//import com.eltex.lab14.feature.events.repository.EventRepository
//import com.eltex.lab14.feature.events.repository.NetworkEventsRepository
//import com.eltex.lab14.feature.events.ui.EventUiModelMapper
//import com.eltex.lab14.feature.events.viewmodel.EventMessage
//import com.eltex.lab14.feature.events.viewmodel.EventStore
//import com.eltex.lab14.feature.events.viewmodel.EventUiState
//
//class EventStoreFactory(
//    private val repository: EventRepository
//) {
//    fun create(): EventStore = EventStore(
//        EventReducer(), EventEffectHandler(
//            repository, EventUiModelMapper()
//        ), setOf(EventMessage.Refresh), EventUiState()
//    )
//}