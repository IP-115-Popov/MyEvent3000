package com.eltex.lab14.feature.events.ui

import com.eltex.lab14.feature.events.data.Event
import javax.inject.Inject

class EventUiModelMapper @Inject constructor(
    private val instantToStringConverter: InstantToStringConverter
) {
    fun map(event: Event): EventUiModel = with(event) {
        EventUiModel(
            id,
            author,
            authorAvatar = authorAvatar,
            content,
            published = instantToStringConverter.Convert(published),
            datetime = instantToStringConverter.Convert(published),
            likedByMe,
            participateByMe,
            likes = likeOwnerIds.size,
            attachment = attachment,
        )
    }
}