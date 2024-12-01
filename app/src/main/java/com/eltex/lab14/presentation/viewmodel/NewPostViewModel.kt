package com.eltex.lab14.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.eltex.lab14.repository.EventRepository

class NewPostViewModel(
    private val repository: EventRepository,
    private val id: Long = 0,
) : ViewModel() {

    fun save(content: String) {
        if (id != 0L)
            repository.updateContentEvent(id ,content)
        else
            repository.addEvent(content)
    }

}