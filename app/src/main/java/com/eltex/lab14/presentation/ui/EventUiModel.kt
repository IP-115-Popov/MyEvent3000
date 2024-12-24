package com.eltex.lab14.presentation.ui

data class EventUiModel(
    val id: Long = 0L,
    val author: String = "",
    val content: String = "",
    val published: String = "",
    val datetime: String = "",
    val likedByMe: Boolean = false,
    val participateByMe: Boolean = false,
    val likes: Int = 0
)