package com.eltex.lab14.data

data class Post(
    val author: String = "",
    val content: String = "",
    val published: String = "",
    val likedByMe: Boolean = false,
    val visitByMe: Boolean = false,
)