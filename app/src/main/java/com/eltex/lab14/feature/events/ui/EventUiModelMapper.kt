package com.eltex.lab14.feature.events.ui

import com.eltex.lab14.feature.events.data.Event
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class EventUiModelMapper @Inject constructor() {

    private companion object {
        val FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm")
    }

    fun map(event: Event): EventUiModel = with(event) {
        EventUiModel(
            id,
            author,
            authorAvatar = authorAvatar,
            content,
            published = FORMATTER.format(published.atZone(ZoneId.systemDefault())),
            datetime = FORMATTER.format(datetime.atZone(ZoneId.systemDefault())),
            likedByMe,
            participateByMe,
            likes = likeOwnerIds.size,
            attachment = attachment,
        )
    }
}