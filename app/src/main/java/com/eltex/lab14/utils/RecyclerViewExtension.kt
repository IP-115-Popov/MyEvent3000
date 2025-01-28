package com.eltex.lab14.utils

import android.view.View
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.loadMoreOnScroll(
    threshold: Int = 3, onLoadMore: () -> Unit
) {
    addOnChildAttachStateChangeListener(object : RecyclerView.OnChildAttachStateChangeListener {
        override fun onChildViewAttachedToWindow(view: View) {
            val itemsCount = adapter?.itemCount ?: 0
            val adapterPosition = getChildAdapterPosition(view)

            if (itemsCount - threshold <= adapterPosition) {
                onLoadMore()
            }
        }

        override fun onChildViewDetachedFromWindow(view: View) = Unit
    })
}