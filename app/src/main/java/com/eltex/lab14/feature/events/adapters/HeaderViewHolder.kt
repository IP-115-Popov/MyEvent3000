package com.eltex.lab14.feature.events.adapters

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.eltex.lab14.databinding.DataHederBinding

class HeaderViewHolder(val binding: DataHederBinding) : ViewHolder(binding.root) {
    fun bind(data: String) {
        binding.root.text = data
    }
}