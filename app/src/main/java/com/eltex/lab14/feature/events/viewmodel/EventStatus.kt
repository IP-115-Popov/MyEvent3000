package com.eltex.lab14.feature.events.viewmodel

sealed interface EventStatus {
    data class Idle(val loadingFinished: Boolean = false) : EventStatus
    data object Refreshing : EventStatus
    data object EmptyLoading : EventStatus
    data object NextPageLoading : EventStatus
    data class EmptyError(val reason: Throwable) : EventStatus
    data class NextPageError(val reason: Throwable) : EventStatus
}