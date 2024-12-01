package com.eltex.lab14.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ToolbarViewModel : ViewModel() {

    private val _saveVisible = MutableStateFlow(false)
    val saveVisible = _saveVisible.asStateFlow()

    private val _saveClicked = MutableStateFlow(false)
    val saveClicked = _saveClicked.asStateFlow()

    fun setSaveVisible(visible: Boolean) {
        _saveVisible.value = visible
    }
    fun onSaveClicked(pending: Boolean) {
        _saveClicked.value = pending
    }
}