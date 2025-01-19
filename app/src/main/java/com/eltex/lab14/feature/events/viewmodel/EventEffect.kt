package com.eltex.lab14.feature.events.viewmodel

import com.eltex.lab14.feature.events.ui.EventUiModel

sealed interface EventEffect {
    data class LoadNextPage(val id: Long, val count: Int) : EventEffect
    data class LoadInitialPage(val count: Int) : EventEffect
    data class Like(val event: EventUiModel) : EventEffect
    data class Delete(val event: EventUiModel) : EventEffect
}