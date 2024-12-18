package com.eltex.lab14.util

import android.content.Context
import com.eltex.lab14.R
import okio.IOException

fun Throwable.getErrorText(context: Context) = when(this) {
    is IOException -> context.getText(R.string.network_error)
    else -> context.getText(R.string.unknown_error)
}