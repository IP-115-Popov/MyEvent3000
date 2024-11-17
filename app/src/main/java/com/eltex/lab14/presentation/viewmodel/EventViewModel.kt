package com.eltex.lab14.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eltex.lab14.repository.EventRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class EventViewModel(private val repository: EventRepository) : ViewModel() {

    private val _state = MutableStateFlow(EventState())
    val state: StateFlow<EventState> = _state.asStateFlow()

    init {
        repository.getPost()
            .onEach { post ->
                _state.update { state ->
                    state.copy(post = post)
                }
            }.launchIn(viewModelScope)
    }

    fun like() {
        repository.like()
    }

    fun participate() {
        repository.participate()
    }
}