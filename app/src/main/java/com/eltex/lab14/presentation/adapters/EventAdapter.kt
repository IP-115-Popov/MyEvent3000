package com.eltex.lab14.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eltex.lab14.R
import com.eltex.lab14.databinding.CardEventBinding
import com.eltex.lab14.databinding.DataHederBinding
import com.eltex.lab14.databinding.ItemErrorBinding
import com.eltex.lab14.databinding.ItemProgressBinding
import com.eltex.lab14.feature.events.ui.EventPagingModel
import com.eltex.lab14.feature.events.ui.EventUiModel
import com.eltex.lab14.presentation.animator.ButtonAnimator
import com.eltex.lab14.presentation.ui.PagingModel

class EventAdapter(
    private val listener: EventListener
) : ListAdapter<EventPagingModel, RecyclerView.ViewHolder>(EventPagingItemCallback()) {

    interface EventListener {
        fun likeClickListener(event: EventUiModel)
        fun participateClickListener(event: EventUiModel)
        fun shareClickListener(event: EventUiModel)
        fun menuClickListener()
        fun onDeleteClickListener(event: EventUiModel)
        fun onUpdateClickListener(event: EventUiModel)
    }

    private companion object {
        private val HEADER_VIEW_TYPE = R.layout.fragment_event
        private val ITEM_VIEW_TYPE = R.layout.item_error
        private val LOADING_VIEW_TYPE = R.layout.card_event
        private val ERROR_VIEW_TYPE = R.layout.card_event
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

            LOADING_VIEW_TYPE -> {
                val binding = ItemProgressBinding.inflate(layoutInflater, parent, false)
                LoadingViewHolder(binding)
            }

            ERROR_VIEW_TYPE -> {
                val binding = ItemErrorBinding.inflate(layoutInflater, parent, false)
                ErrorViewHolder(binding)
            }

            else -> error("Unknown viewType: $viewType")
        }
    }


    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is PagingModel.Data -> ITEM_VIEW_TYPE
            is PagingModel.Error -> ERROR_VIEW_TYPE
            is PagingModel.Header -> HEADER_VIEW_TYPE
            PagingModel.Loading -> LOADING_VIEW_TYPE
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
            is PagingModel.Data -> {
                val eventViewHolder = holder as EventViewHolder
                val event = item.value // Получаем сам объект Event
                eventViewHolder.bind(event)

                // Устанавливаем обработчики кликов в onBindViewHolder
                eventViewHolder.binding.bthLike.setOnClickListener {
                    listener.likeClickListener(event)
                    ButtonAnimator.animationRotate(eventViewHolder.binding.bthLike)
                }
                eventViewHolder.binding.bthParticipate.setOnClickListener {
                    listener.participateClickListener(event)
                    ButtonAnimator.animationRotate(eventViewHolder.binding.bthParticipate)
                }
                eventViewHolder.binding.bthShare.setOnClickListener {
                    listener.shareClickListener(event)
                    ButtonAnimator.animationRotate(eventViewHolder.binding.bthShare)
                }
                eventViewHolder.binding.imvMenu.setOnClickListener {
                    listener.menuClickListener()
                    ButtonAnimator.animationRotate(eventViewHolder.binding.imvMenu)
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

            is PagingModel.Header -> (holder as HeaderViewHolder).bind(item.title)
            is PagingModel.Error -> (holder as ErrorViewHolder).bind(item.error)
            PagingModel.Loading -> holder as LoadingViewHolder
        }
    }
}