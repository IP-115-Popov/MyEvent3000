package com.eltex.lab14.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eltex.lab14.data.Event
import com.eltex.lab14.repository.EventRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class EventViewModel(private val repository: EventRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(EventUiState())
    val uiState: StateFlow<EventUiState> = _uiState.asStateFlow()

    init {
        repository.getEvent().onEach { events: List<Event> ->
            _uiState.update { it ->
                it.copy(events = events)
            }
        }.launchIn(viewModelScope)
    }

    fun likeById(id: Long) {
        repository.likeById(id)
    }

    fun participateById(id: Long) {
        repository.participateById(id)
    }

    fun addEvent(content: String) {
        repository.addEvent(content)
    }

    fun updateEvent(id: Long, newContent: String) {
        repository.updateContentEvent(id, newContent)
    }

    fun deleteById(id: Long) {
        repository.deleteById(id)
    }

}