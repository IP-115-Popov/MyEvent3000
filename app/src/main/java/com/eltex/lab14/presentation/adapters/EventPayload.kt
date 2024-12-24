package com.eltex.lab14.presentation.adapters

data class EventPayload(
    val likedByMe: Boolean? = null,
    val participateByMe: Boolean? = null,
    val likes: Int? = null
) {
    fun isNotEmpty(): Boolean = likedByMe != null || participateByMe != null || likes != null
}
