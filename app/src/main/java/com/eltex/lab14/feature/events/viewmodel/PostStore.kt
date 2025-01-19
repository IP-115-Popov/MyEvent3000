package com.eltex.lab14.feature.events.viewmodel

import com.eltex.lab14.mvi.Store

typealias EventStore = Store<EventUiState, EventMessage, EventEffect>