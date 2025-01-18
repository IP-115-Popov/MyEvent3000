package com.eltex.lab14.feature.events.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eltex.lab14.feature.events.data.Event
import com.eltex.lab14.feature.events.repository.EventRepository
import com.eltex.lab14.feature.events.ui.EventUiModelMapper
import com.eltex.lab14.util.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EventViewModel(
    private val repository: EventRepository
) : ViewModel() {

    private val mapper = EventUiModelMapper()

    private val _uiState = MutableStateFlow(EventUiState())
    val uiState: StateFlow<EventUiState> = _uiState.asStateFlow()

    init {
        load()
    }

    fun load() {
        _uiState.update { it.copy(status = Status.Loading) }

        viewModelScope.launch {
            try {
                val updatedEvent = repository.getEvent()

                val updatedEventUiModel = withContext(Dispatchers.Default) {
                    updatedEvent?.map { event: Event ->
                        mapper.map(event)
                    }
                }
                _uiState.update {
                    it.copy(
                        status = Status.Idle, events = updatedEventUiModel
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(status = Status.Error(e)) }
            }
        }
    }

    fun likeById(id: Long) {
        viewModelScope.launch {
            uiState.value.events?.find { it.id == id }?.let { event ->
                try {
                    val event = if (event.likedByMe) {
                        repository.deleteLikeById(id)
                    } else {
                        repository.likeById(id)
                    }

                    withContext(Dispatchers.Default) {
                        _uiState.update {
                            it.copy(
                                events = it.events?.map {
                                    if (it.id == event.id) {
                                        mapper.map(event)
                                    } else {
                                        it
                                    }
                                },
                                status = Status.Idle
                            )
                        }
                    }
                } catch (e: Exception) {
                    _uiState.update { it.copy(status = Status.Error(e)) }
                }
            }
        }
    }

    fun participateById(id: Long) {
        viewModelScope.launch {
            uiState.value.events?.find { it.id == id }?.let { event ->
                try {
                    val event = if (event.likedByMe) {
                        repository.deleteParticipateById(id)
                    } else {
                        repository.participateById(id)
                    }
                    withContext(Dispatchers.Default) {
                        _uiState.update {
                            it.copy(
                                events = it.events?.map {
                                    if (it.id == event.id) {
                                        mapper.map(event)
                                    } else {
                                        it
                                    }
                                },
                                status = Status.Idle
                            )
                        }
                    }
                } catch (e: Exception) {
                    _uiState.update { it.copy(status = Status.Error(e)) }
                }

            }
        }
    }

    fun deleteById(id: Long) {
        viewModelScope.launch {
            try {
                repository.deleteById(id)
                load()
                _uiState.update {
                    it.copy(
                        status = Status.Idle,
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(status = Status.Error(e)) }
            }
        }
    }

    fun consumeError() {
        _uiState.update { it.copy(status = Status.Idle) }
    }
}