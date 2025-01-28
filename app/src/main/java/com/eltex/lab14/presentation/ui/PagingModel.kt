package com.eltex.lab14.presentation.ui

sealed interface PagingModel<out T> {
    data class Data<T>(val value: T) : PagingModel<T>
    data class Error(val error: Throwable) : PagingModel<Nothing>
    data object Loading : PagingModel<Nothing>
    data class Header(val title: String) : PagingModel<Nothing>
}