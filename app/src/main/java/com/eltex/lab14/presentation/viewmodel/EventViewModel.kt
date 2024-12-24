package com.eltex.lab14.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.eltex.lab14.data.Event
import com.eltex.lab14.repository.EventRepository
import com.eltex.lab14.util.Callback
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class EventViewModel(private val repository: EventRepository) : ViewModel() {

    private val disposable = CompositeDisposable()

    private val _uiState = MutableStateFlow(EventUiState())
    val uiState: StateFlow<EventUiState> = _uiState.asStateFlow()

    init {
        load()
    }

    fun load() {
        _uiState.update { it.copy(status = Status.Loading) }

        repository.getEvent()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { events ->
                    _uiState.update {
                        it.copy(
                            status = Status.Idle, events = events
                        )
                    }
                },
                onError = { throwable->
                    _uiState.update { it.copy(status = Status.Error(throwable)) }
                }
            )
            .addTo(disposable)
    }

    fun likeById(id: Long) {
        uiState.value.events?.find { it.id == id }?.let {
            when (it.likedByMe) {
                true -> {
                    repository.deleteLikeById(id)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeBy(
                            onSuccess = { events ->
                                _uiState.update {
                                    load()
                                    it.copy(
                                        status = Status.Idle,
                                    )
                                }
                            },
                            onError = { throwable->
                                _uiState.update { it.copy(status = Status.Error(throwable)) }
                            }
                        )
                        .addTo(disposable)
                }

                false -> {
                    repository.likeById(id)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeBy(
                            onSuccess = { events ->
                                _uiState.update {
                                    load()
                                    it.copy(
                                        status = Status.Idle,
                                    )
                                }
                            },
                            onError = { throwable->
                                _uiState.update { it.copy(status = Status.Error(throwable)) }
                            }
                        )
                        .addTo(disposable)
                }
            }
        }
    }

    fun participateById(id: Long) {
        uiState.value.events?.find { it.id == id }?.let {
            when (it.participateByMe) {
                true -> {
                    repository.participateById(id)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeBy(
                            onSuccess = { events ->
                                _uiState.update {
                                    load()
                                    it.copy(
                                        status = Status.Idle,
                                    )
                                }
                            },
                            onError = { throwable->
                                _uiState.update { it.copy(status = Status.Error(throwable)) }
                            }
                        )
                        .addTo(disposable)
                }

                false -> {
                    repository.deleteParticipateById(id)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeBy(
                            onSuccess = { events ->
                                _uiState.update {
                                    load()
                                    it.copy(
                                        status = Status.Idle,
                                    )
                                }
                            },
                            onError = { throwable->
                                _uiState.update { it.copy(status = Status.Error(throwable)) }
                            }
                        )
                        .addTo(disposable)
                }
            }
        }
    }

    fun deleteById(id: Long) {
        repository.deleteById(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = {
                    _uiState.update {
                        load()
                        it.copy(
                            status = Status.Idle,
                        )
                    }
                },
                onError = { throwable->
                    _uiState.update { it.copy(status = Status.Error(throwable)) }
                }
            )
            .addTo(disposable)
    }

    fun consumeError() {
        _uiState.update { it.copy(status = Status.Idle) }
    }

    override fun onCleared() {
        disposable.dispose()
    }
}