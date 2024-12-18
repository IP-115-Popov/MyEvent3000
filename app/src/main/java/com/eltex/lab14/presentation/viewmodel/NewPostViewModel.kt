package com.eltex.lab14.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.eltex.lab14.data.Event
import com.eltex.lab14.repository.EventRepository
import com.eltex.lab14.util.Callback
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NewPostViewModel(
    private val repository: EventRepository,
    private val id: Long = 0,
) : ViewModel() {

    private val _state = MutableStateFlow(NewEventState())
    val state = _state.asStateFlow()

    fun save(content: String) {
        _state.update { it.copy(status = Status.Loading) }
        repository.save(id, content, object : Callback<Event> {
            override fun onSuccess(data: Event) {
                _state.update { it.copy(status = Status.Idle, event = data) }
            }

            override fun onError(exception: Exception) {
                _state.update { it.copy(status = Status.Error(exception)) }
            }

        })
    }

    fun consumeError() {
        _state.update { it.copy(status = Status.Idle) }
    }

}