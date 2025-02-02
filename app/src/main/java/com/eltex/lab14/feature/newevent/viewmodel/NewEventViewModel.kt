package com.eltex.lab14.feature.newevent.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eltex.lab14.feature.events.repository.EventRepository
import com.eltex.lab14.util.Status
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = NewEventViewModel.ViewModelFactory::class)
class NewEventViewModel @AssistedInject constructor(
    private val repository: EventRepository,
    @Assisted private val id: Long = 0,
) : ViewModel() {

    private val _state = MutableStateFlow(NewEventState())
    val state = _state.asStateFlow()

    fun save(content: String) {
        _state.update { it.copy(status = Status.Loading) }

        viewModelScope.launch {
            try {
                val event = repository.save(id, content, state.value.file)
                _state.update {
                    it.copy(status = Status.Idle, event = event)
                }
            } catch (e: Exception) {
                _state.update { it.copy(status = Status.Error(e)) }
            }
        }

    }

    fun saveAttachment(file: FileModel?) {
        _state.update { it.copy(file = file) }
    }

    fun consumeError() {
        _state.update { it.copy(status = Status.Idle) }
    }

    @AssistedFactory
    interface ViewModelFactory {
        fun create(id: Long): NewEventViewModel
    }
}