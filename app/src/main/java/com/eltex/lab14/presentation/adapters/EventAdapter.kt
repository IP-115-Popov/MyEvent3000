package com.eltex.lab14.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eltex.lab14.R
import com.eltex.lab14.data.Event
import com.eltex.lab14.databinding.CardEventBinding
import com.eltex.lab14.databinding.DataHederBinding
import com.eltex.lab14.presentation.animator.MyAnimator

class EventAdapter(
    private val listener: EventListener
) : ListAdapter<EventItem, RecyclerView.ViewHolder>(EventItemCallback()) {

    interface EventListener {
        fun likeClickListener(event: Event)
        fun participateClickListener(event: Event)
        fun shareClickListener(event: Event)
        fun menuClickListener()
        fun onDeleteClickListener(event: Event)
        fun onUpdateClickListener(event: Event)
    }

    private val HEADER_VIEW_TYPE = 0
    private val ITEM_VIEW_TYPE = 1

    fun submitMyList(events: List<Event>) {
        val items =
            events.groupBy { it.published } // Группируем по дате публикации (или любому другому признаку)
        val items2 = mutableListOf<EventItem>()

        for ((publishedDate, eventList) in items) {
            // Добавляем заголовок
            items2.add(EventItem.Header(publishedDate))
            // Добавляем все события под этим заголовком
            items2.addAll(eventList.map { EventItem.Event(it) })
        }

        submitList(items2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            HEADER_VIEW_TYPE -> {
                val binding = DataHederBinding.inflate(layoutInflater, parent, false)
                HeaderViewHolder(binding)
            }

            ITEM_VIEW_TYPE -> {
                val binding = CardEventBinding.inflate(layoutInflater, parent, false)
                EventViewHolder(binding)
            }

            else -> throw IllegalArgumentException("Unknown viewType")
        }
    }


    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is EventItem.Header -> HEADER_VIEW_TYPE
            is EventItem.Event -> ITEM_VIEW_TYPE
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>
    ) {
        if (holder is EventViewHolder) {
            if (payloads.isEmpty()) {
                onBindViewHolder(holder, position)
            } else {
                payloads.forEach {
                    if (it is EventPayload) {
                        holder.bind(it)
                    }
                }
            }
        } else {
            onBindViewHolder(holder, position)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is EventItem.Event -> {
                val eventViewHolder = holder as EventViewHolder
                val event = item.event // Получаем сам объект Event
                eventViewHolder.bind(event)

                // Устанавливаем обработчики кликов в onBindViewHolder
                eventViewHolder.binding.bthLike.setOnClickListener {
                    listener.likeClickListener(event)
                    MyAnimator.animationRotate(eventViewHolder.binding.bthLike)
                }
                eventViewHolder.binding.bthParticipate.setOnClickListener {
                    listener.participateClickListener(event)
                    MyAnimator.animationRotate(eventViewHolder.binding.bthParticipate)
                }
                eventViewHolder.binding.bthShare.setOnClickListener {
                    listener.shareClickListener(event)
                    MyAnimator.animationRotate(eventViewHolder.binding.bthShare)
                }
                eventViewHolder.binding.imvMenu.setOnClickListener {
                    listener.menuClickListener()
                    MyAnimator.animationRotate(eventViewHolder.binding.imvMenu)
                    PopupMenu(it.context, it).apply {
                        inflate(R.menu.post_menu)
                        setOnMenuItemClickListener { menuItem ->
                            when (menuItem.itemId) {
                                R.id.delete_post -> {
                                    listener.onDeleteClickListener(event)
                                    true
                                }

                                R.id.update_post -> {
                                    listener.onUpdateClickListener(event)
                                    true
                                }

                                else -> false
                            }
                        }

                        show()
                    }
                }
            }

            is EventItem.Header -> {
                val headerViewHolder = holder as HeaderViewHolder
                headerViewHolder.bind(item.title)
            }
        }
    }
}

sealed class EventItem {
    data class Header(val title: String) : EventItem()
    data class Event(val event: com.eltex.lab14.data.Event) : EventItem()
}