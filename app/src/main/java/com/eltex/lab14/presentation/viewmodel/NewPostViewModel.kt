package com.eltex.lab14.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eltex.lab14.repository.EventRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewPostViewModel(
    private val repository: EventRepository,
    private val id: Long = 0,
) : ViewModel() {

    private val _state = MutableStateFlow(NewEventState())
    val state = _state.asStateFlow()

    fun save(content: String) {
        _state.update { it.copy(status = Status.Loading) }

        viewModelScope.launch {
            try {
                val event = repository.save(id, content)
                _state.update {
                    it.copy(status = Status.Idle, event = event)
                }
            } catch (e: Exception) {
                _state.update { it.copy(status = Status.Error(e)) }
            }
        }

    }

    fun consumeError() {
        _state.update { it.copy(status = Status.Idle) }
    }
}