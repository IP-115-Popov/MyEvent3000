package com.eltex.lab14.presentation.viewmodel

import com.eltex.lab14.data.Event

data class NewEventState(
    val event: Event? = null,
    val status: Status = Status.Idle
)
