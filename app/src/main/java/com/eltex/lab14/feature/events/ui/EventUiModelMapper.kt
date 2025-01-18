package com.eltex.lab14.feature.events.ui

import com.eltex.lab14.feature.events.data.Event
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class EventUiModelMapper {

    private companion object {
        val FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm")
    }

    fun map(event: Event): EventUiModel = with(event) {
        EventUiModel(
            id,
            author,
            content,
            published = FORMATTER.format(published.atZone(ZoneId.systemDefault())),
            datetime = FORMATTER.format(datetime.atZone(ZoneId.systemDefault())),
            likedByMe,
            participateByMe,
            likes = likeOwnerIds.size
        )
    }
}