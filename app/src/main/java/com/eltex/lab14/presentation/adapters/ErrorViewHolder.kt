package com.eltex.lab14.presentation.adapters

import androidx.recyclerview.widget.RecyclerView
import com.eltex.lab14.databinding.ItemErrorBinding
import com.eltex.lab14.util.getErrorText

class ErrorViewHolder(private val binding: ItemErrorBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(error: Throwable) {
        binding.errorText.text = error.getErrorText(binding.root.context)
    }
}