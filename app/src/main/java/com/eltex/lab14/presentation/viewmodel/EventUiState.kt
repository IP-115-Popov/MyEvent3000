package com.eltex.lab14.presentation.viewmodel

import com.eltex.lab14.data.Event
import com.eltex.lab14.presentation.ui.EventUiModel

data class EventUiState(
    val events: List<EventUiModel>? = null, val status: Status = Status.Idle
) {
    val isRefreshing: Boolean
        get() = status == Status.Loading && events?.isNotEmpty() == true

    val isEmptyLoading: Boolean
        get() = status == Status.Loading && events.isNullOrEmpty()

    val isRefreshError: Boolean
        get() = status is Status.Error && events?.isNotEmpty() == true

    val isEmptyError: Boolean
        get() = status is Status.Error && events.isNullOrEmpty()
}