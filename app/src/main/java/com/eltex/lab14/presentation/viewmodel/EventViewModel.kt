package com.eltex.lab14.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.eltex.lab14.data.Event
import com.eltex.lab14.repository.EventRepository
import com.eltex.lab14.util.Callback
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class EventViewModel(private val repository: EventRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(EventUiState())
    val uiState: StateFlow<EventUiState> = _uiState.asStateFlow()

    init {
        load()
    }

    fun load() {
        _uiState.update { it.copy(status = Status.Loading) }
        repository.getEvent(object : Callback<List<Event>> {
            override fun onSuccess(data: List<Event>) {
                _uiState.update {
                    it.copy(
                        status = Status.Idle, events = data
                    )
                }
            }

            override fun onError(exception: Throwable) {
                _uiState.update { it.copy(status = Status.Error(exception)) }
            }
        })
    }

    fun likeById(id: Long) {
        uiState.value.events?.find { it.id == id }?.let {
            when (it.likedByMe) {
                true -> {
                    repository.deleteLikeById(id, object : Callback<Event> {
                        override fun onSuccess(data: Event) {
                            _uiState.update {
                                load()
                                it.copy(
                                    status = Status.Idle,
                                )
                            }
                        }

                        override fun onError(exception: Throwable) {
                            _uiState.update { it.copy(status = Status.Error(exception)) }
                        }
                    })
                }

                false -> {
                    repository.likeById(id, object : Callback<Event> {
                        override fun onSuccess(data: Event) {
                            _uiState.update {
                                load()
                                it.copy(
                                    status = Status.Idle,
                                )
                            }
                        }

                        override fun onError(exception: Throwable) {
                            _uiState.update { it.copy(status = Status.Error(exception)) }
                        }
                    })
                }
            }
        }
    }

    fun participateById(id: Long) {
        //TODO
//        uiState.value.events?.find { it.id == id }?.let {
//            when (it.participateByMe) {
//                true -> {
//                    repository.deleteParticipateById(id, object : Callback<Event> {
//                        override fun onSuccess(data: Event) {
//                            _uiState.update {
//                                load()
//                                it.copy(
//                                    status = Status.Idle,
//                                )
//                            }
//                        }
//
//                        override fun onError(exception: java.lang.Exception) {
//                            _uiState.update { it.copy(status = Status.Error(exception)) }
//                        }
//                    })
//
//                }
//
//                false -> {
//                    repository.participateById(id, object : Callback<Event> {
//                        override fun onSuccess(data: Event) {
//                            _uiState.update {
//                                load()
//                                it.copy(
//                                    status = Status.Idle,
//                                )
//                            }
//                        }
//
//                        override fun onError(exception: java.lang.Exception) {
//                            _uiState.update { it.copy(status = Status.Error(exception)) }
//                        }
//                    })
//                }
//            }
//        }
    }

    fun addEvent(content: String) {
        TODO()
    }

    fun updateEvent(id: Long, newContent: String) {
        TODO()
    }

    fun deleteById(id: Long) {
        //TODO
//        repository.deleteById(id, object : Callback<Unit> {
//            override fun onSuccess(data: Unit) {
//                _uiState.update {
//                    load()
//                    it.copy(
//                        status = Status.Idle,
//                    )
//                }
//            }
//
//            override fun onError(exception: java.lang.Exception) {
//                _uiState.update { it.copy(status = Status.Error(exception)) }
//            }
//        })
    }

    fun consumeError() {
        _uiState.update { it.copy(status = Status.Idle) }
    }

}