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
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NewPostViewModel(
    private val repository: EventRepository,
    private val id: Long = 0,
) : ViewModel() {

    private val disposable = CompositeDisposable()

    private val _state = MutableStateFlow(NewEventState())
    val state = _state.asStateFlow()

    fun save(content: String) {
        _state.update { it.copy(status = Status.Loading) }

        repository.save(id, content)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { events ->
                    _state.update { it.copy(status = Status.Idle, event = events) }
                },
                onError = { throwable->
                    _state.update { it.copy(status = Status.Error(throwable)) }
                }
            )
            .addTo(disposable)
    }

    fun consumeError() {
        _state.update { it.copy(status = Status.Idle) }
    }

    override fun onCleared() {
        disposable.dispose()
    }
}