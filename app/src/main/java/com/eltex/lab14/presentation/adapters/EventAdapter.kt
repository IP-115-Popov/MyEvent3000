package com.eltex.lab14.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eltex.lab14.R
import com.eltex.lab14.data.Event
import com.eltex.lab14.databinding.CardEventBinding
import com.eltex.lab14.utils.toast

class EventAdapter(
    private val likeClickListener: (event : Event) -> Unit,
    private val participateClickListener: (event : Event) -> Unit,
    private val ShareClickListener: () -> Unit,
    private val MenuClickListener: () -> Unit,

) : ListAdapter<Event, EventViewHolder>(EventItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CardEventBinding.inflate(layoutInflater, parent, false)

        val viewHolder = EventViewHolder(binding)

        binding.bthLike.setOnClickListener {
            likeClickListener(getItem(viewHolder.adapterPosition))
        }
        binding.bthPeopleOutline.setOnClickListener {
            participateClickListener(getItem(viewHolder.adapterPosition))
        }
        binding.bthShare.setOnClickListener {
            ShareClickListener()
        }
        binding.imvMenu.setOnClickListener {
            MenuClickListener()
        }
        return viewHolder
    }

    override fun onBindViewHolder(
        holder: EventViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            payloads.forEach {
                if (it is EventPayload) {
                    holder.bind(it)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}