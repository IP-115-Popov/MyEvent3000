package com.eltex.lab14.feature.events.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    private val store: EventStore
) : ViewModel() {
    val uiState: StateFlow<EventUiState> = store.state

    init {
        viewModelScope.launch {
            store.connect()
        }
    }

    fun accept(message: EventMessage) {
        store.accept(message)
    }
}