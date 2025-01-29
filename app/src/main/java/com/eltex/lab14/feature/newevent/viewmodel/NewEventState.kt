package com.eltex.lab14.feature.newevent.viewmodel

import com.eltex.lab14.feature.events.data.Event
import com.eltex.lab14.util.Status

data class NewEventState(
    val event: Event? = null,
    val status: Status = Status.Idle,
    val file: FileModel? = null
)
