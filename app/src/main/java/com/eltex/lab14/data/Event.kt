package com.eltex.lab14.data


data class Event(
    val id: Long,
    val author: String = "",
    val content: String = "",
    val published: String = "",
    val likedByMe: Boolean = false,
    val participateByMe: Boolean = false,
)