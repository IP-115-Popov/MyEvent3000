package com.eltex.lab14.feature.events.data

import com.eltex.lab14.feature.media.Attachment
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class Event(
    @SerialName("id") val id: Long = 0L,
    @SerialName("author") val author: String = "",
    @SerialName("authorAvatar") val authorAvatar: String? = null,
    @SerialName("content") val content: String = "",
    @Serializable(with = InstantSerializer::class)
    @SerialName("published") val published: Instant = Instant.EPOCH,
    @Serializable(with = InstantSerializer::class)
    @SerialName("datetime") val datetime: Instant = Instant.EPOCH,
    @SerialName("likedByMe") val likedByMe: Boolean = false,
    @SerialName("participateByMe") val participateByMe: Boolean = false,
    @SerialName("likeOwnerIds") val likeOwnerIds: Set<Long> = emptySet(),
    @SerialName("attachment")
    val attachment: Attachment? = null
)