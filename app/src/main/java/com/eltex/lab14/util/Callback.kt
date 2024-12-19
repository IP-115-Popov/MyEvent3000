package com.eltex.lab14.util

interface Callback<T> {
    fun onSuccess(data: T)
    fun onError(exception: Throwable)
}
