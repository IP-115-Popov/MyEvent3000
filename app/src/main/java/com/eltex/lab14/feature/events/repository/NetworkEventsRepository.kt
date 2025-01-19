package com.eltex.lab14.feature.events.repository

import com.eltex.lab14.feature.events.api.EventApi
import com.eltex.lab14.feature.events.data.Event
import java.time.Instant

class NetworkEventsRepository : EventRepository {
    override suspend fun getBefore(id: Long, count: Int): List<Event> =
        EventApi.INSTANCE.getBefore(id, count)

    override suspend fun getLatest(count: Int): List<Event> = EventApi.INSTANCE.getLatest(count)

    override suspend fun getEvent() = EventApi.INSTANCE.getAll()

    override suspend fun likeById(id: Long): Event = EventApi.INSTANCE.likeById(id)

    override suspend fun deleteLikeById(id: Long): Event = EventApi.INSTANCE.deleteLikeById(id)

    override suspend fun participateById(id: Long): Event = EventApi.INSTANCE.participateById(id)

    override suspend fun deleteParticipateById(id: Long): Event =
        EventApi.INSTANCE.deleteParticipateById(id)

    override suspend fun save(id: Long, content: String): Event = EventApi.INSTANCE.savEvent(
        Event(
            id = id,
            content = content,
            datetime = Instant.now(),
        )
    )

    override suspend fun deleteById(id: Long) = EventApi.INSTANCE.deleteById(id)

}