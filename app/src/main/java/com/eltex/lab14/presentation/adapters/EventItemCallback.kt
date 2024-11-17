package com.eltex.lab14.presentation.adapters

import androidx.recyclerview.widget.DiffUtil

class EventItemCallback : DiffUtil.ItemCallback<EventItem>() {
    override fun areItemsTheSame(oldItem: EventItem, newItem: EventItem): Boolean {
        return when {
            oldItem is EventItem.Header && newItem is EventItem.Header -> oldItem.title == newItem.title
            oldItem is EventItem.Event && newItem is EventItem.Event -> oldItem.event.id == newItem.event.id
            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: EventItem, newItem: EventItem): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: EventItem, newItem: EventItem): Any? {
        return when {
            oldItem is EventItem.Event && newItem is EventItem.Event -> {
                EventPayload(
                    likedByMe = newItem.event.likedByMe.takeIf { it != oldItem.event.likedByMe },
                    participateByMe = newItem.event.participateByMe.takeIf { it != oldItem.event.participateByMe }
                ).takeIf { it.isNotEmpty() }
            }
            else -> null
        }
    }
}
