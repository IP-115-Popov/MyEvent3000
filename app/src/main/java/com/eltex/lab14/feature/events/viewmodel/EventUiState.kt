package com.eltex.lab14.feature.events.viewmodel

import com.eltex.lab14.feature.events.ui.EventUiModel

data class EventUiState(
    val events: List<EventUiModel> = emptyList(),
    val status: EventStatus = EventStatus.Idle,
    val singleError: Throwable? = null
) {
    val isEmptyError: Boolean = status is EventStatus.EmptyError

    val isRefreshing: Boolean = status == EventStatus.Refreshing

    val emptyError: Throwable? = (status as? EventStatus.EmptyError)?.reason

    val isEmptyLoading: Boolean = status == EventStatus.EmptyLoading
}