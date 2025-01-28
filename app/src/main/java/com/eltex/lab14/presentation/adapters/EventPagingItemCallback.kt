package com.eltex.lab14.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.eltex.lab14.feature.events.ui.EventPagingModel
import com.eltex.lab14.presentation.ui.PagingModel

class EventPagingItemCallback:  DiffUtil.ItemCallback<EventPagingModel>() {
    private val delegate = EventItemCallback()

    override fun areItemsTheSame(oldItem: EventPagingModel, newItem: EventPagingModel): Boolean {
        if (oldItem::class != newItem::class) {
            return false
        }

        return if (oldItem is PagingModel.Data && newItem is PagingModel.Data) {
            delegate.areContentsTheSame(oldItem.value, newItem.value)
        } else {
            oldItem == newItem
        }
    }

    override fun areContentsTheSame(oldItem: EventPagingModel, newItem: EventPagingModel): Boolean =
        oldItem == newItem

    override fun getChangePayload(oldItem: EventPagingModel, newItem: EventPagingModel): Any? {
        if (oldItem::class != newItem::class) {
            return false
        }

        return if (oldItem is PagingModel.Data && newItem is PagingModel.Data) {
            delegate.areContentsTheSame(oldItem.value, newItem.value)
        } else {
            null
        }
    }
}