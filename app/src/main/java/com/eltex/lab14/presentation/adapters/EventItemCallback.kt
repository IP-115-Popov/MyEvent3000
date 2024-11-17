package com.eltex.lab14.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.eltex.lab14.data.Event

class EventItemCallback : DiffUtil.ItemCallback<Event>() {
    override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean = oldItem == newItem

    override fun getChangePayload(oldItem: Event, newItem: Event): Any? =
        EventPayload(likedByMe = newItem.likedByMe.takeIf { it != oldItem.likedByMe },
            participateByMe = newItem.participateByMe.takeIf { it != oldItem.participateByMe }).takeIf {
                it.isNotEmpty()
            }
}