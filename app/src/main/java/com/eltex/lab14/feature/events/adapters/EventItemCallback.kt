package com.eltex.lab14.feature.events.adapters

import androidx.recyclerview.widget.DiffUtil
import com.eltex.lab14.feature.events.ui.EventUiModel

class EventItemCallback : DiffUtil.ItemCallback<EventUiModel>() {
    override fun areItemsTheSame(oldItem: EventUiModel, newItem: EventUiModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: EventUiModel, newItem: EventUiModel): Boolean =
        oldItem == newItem

    override fun getChangePayload(oldItem: EventUiModel, newItem: EventUiModel): Any? =
        EventPayload(
            likedByMe = newItem.likedByMe.takeIf { it != oldItem.likedByMe },
            participateByMe = newItem.participateByMe.takeIf { it != oldItem.participateByMe },
            likes = newItem.likes.takeIf { it != oldItem.likes }
        ).takeIf { it.isNotEmpty() }

}
