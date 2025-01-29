package com.eltex.lab14.feature.media

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MediaDto(
    @SerialName("url")
    val url: String
)
