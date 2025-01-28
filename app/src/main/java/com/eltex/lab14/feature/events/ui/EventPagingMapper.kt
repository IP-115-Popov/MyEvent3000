package com.eltex.lab14.feature.events.ui

import com.eltex.lab14.feature.events.viewmodel.EventStatus
import com.eltex.lab14.feature.events.viewmodel.EventUiState
import com.eltex.lab14.presentation.ui.PagingModel

object EventPagingMapper {
    fun map(state: EventUiState): List<PagingModel<EventUiModel>> {
        val event = state.events.map {
            PagingModel.Data(it)
        }

        return when (val statusValue = state.status) {
            EventStatus.NextPageLoading -> event + PagingModel.Loading
            is EventStatus.NextPageError -> event + PagingModel.Error(statusValue.reason)
            is EventStatus.EmptyError,
            EventStatus.EmptyLoading,
            is EventStatus.Idle,
            EventStatus.Refreshing -> event
        }
    }
}