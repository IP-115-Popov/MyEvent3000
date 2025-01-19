package com.eltex.lab14.feature.events.model

import com.eltex.lab14.feature.events.ui.EventUiModel

data class EventWithError(
    val event: EventUiModel,
    val throwable: Throwable
)