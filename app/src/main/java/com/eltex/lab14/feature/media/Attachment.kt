package com.eltex.lab14.feature.media

import com.eltex.lab14.feature.events.data.AttachmentType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Attachment(
    @SerialName("url")
    val url: String,
    @SerialName("type")
    val type: AttachmentType,
)
