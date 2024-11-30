package com.eltex.lab14.utils

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.annotation.StringRes
import com.eltex.lab14.R

fun Context.toast(@StringRes res: Int, short: Boolean = true) {
    val length = if (short) Toast.LENGTH_SHORT else Toast.LENGTH_LONG
    Toast.makeText(this, getString(res), length).show()
}

fun Context.share(text: String) {
    val intent = Intent.createChooser(
        Intent(Intent.ACTION_SEND).putExtra(Intent.EXTRA_TEXT, text).setType("text/plain"), null
    )
    runCatching {
        startActivity(intent)
    }.onFailure {
        toast(R.string.app_not_found, false)
    }
}