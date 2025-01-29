package com.eltex.lab14.feature.events.adapters

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.eltex.lab14.R
import com.eltex.lab14.databinding.CardEventBinding
import com.eltex.lab14.feature.events.ui.EventUiModel

class EventViewHolder(val binding: CardEventBinding) : ViewHolder(binding.root) {
    fun bind(event: EventUiModel) {
        binding.tvAuthor.text = event.author
        binding.tvContent.text = event.content
        binding.tvData.text = event.published
        binding.tvInitial.text = event.author.take(1)
        updateParticipate(event.participateByMe)
        updateLike(event.likedByMe)
        binding.bthLike.text = event.likes.toString()

        Glide.with(binding.root)
            .load(event.authorAvatar)
            .placeholder(R.drawable.avatar_background)
            .into(binding.imvAvatar)

        Glide.with(binding.root)
            .load(event.attachment?.url)
            .into(binding.attachment)
    }

    fun bind(payload: EventPayload) {
        payload.likedByMe?.let { likedByMe ->
            updateLike(likedByMe)
        }
        payload.participateByMe?.let { participateByMe ->
            updateParticipate(participateByMe)
        }
        payload.likes?.let {
            binding.bthLike.text = it.toString()
        }
    }

    private fun updateParticipate(participateByMe: Boolean) {
        binding.bthParticipate.text = if (participateByMe) "1" else "0"
    }

    private fun updateLike(likedByMe: Boolean) {
        binding.bthLike.isSelected = likedByMe
    }
}