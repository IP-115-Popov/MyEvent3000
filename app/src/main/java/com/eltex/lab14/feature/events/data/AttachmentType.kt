package com.eltex.lab14.feature.events.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class AttachmentType {
    @SerialName("IMAGE")
    IMAGE,

    @SerialName("VIDEO")
    VIDEO,

    @SerialName("AUDIO")
    AUDIO
}