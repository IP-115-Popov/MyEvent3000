package com.eltex.lab14.presentation.adapters

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.eltex.lab14.data.Event
import com.eltex.lab14.databinding.CardEventBinding

class EventViewHolder(private val binding: CardEventBinding) : ViewHolder(binding.root) {
    fun bind(event: Event) {
        binding.tvAuthor.text = event.author
        binding.tvContent.text = event.content
        binding.tvData.text = event.published
        binding.tvInitial.text = event.author.take(1)
        updateParticipate(event.participateByMe)
        updateLike(event.likedByMe)
    }

    fun bind(payload: EventPayload) {
        payload.likedByMe?.let { likedByMe ->
            updateLike(likedByMe)
        }
        payload.participateByMe?.let { participateByMe ->
            updateParticipate(participateByMe)
        }
    }

    private fun updateParticipate(participateByMe: Boolean) {
        binding.bthPeopleOutline.text = if (participateByMe) "1" else "0"
    }

    private fun updateLike(likedByMe: Boolean) {
        binding.bthLike.isSelected = likedByMe
        binding.bthLike.text = if (likedByMe) "1" else "0"
    }
}