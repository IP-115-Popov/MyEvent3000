package com.eltex.lab14.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Event(
    @SerialName("id") val id: Long = 0L,
    @SerialName("author") val author: String = "",
    @SerialName("content") val content: String = "",
    @SerialName("published") val published: String = "",
    @SerialName("likedByMe") val likedByMe: Boolean = false,
    @SerialName("participateByMe") val participateByMe: Boolean = false,
)