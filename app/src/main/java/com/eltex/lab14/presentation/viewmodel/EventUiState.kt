package com.eltex.lab14.presentation.viewmodel

import com.eltex.lab14.data.Event

data class EventUiState(
    val events: List<Event> = emptyList()
)