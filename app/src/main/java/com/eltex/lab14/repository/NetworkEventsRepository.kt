package com.eltex.lab14.repository

import com.eltex.lab14.api.EventApi
import com.eltex.lab14.data.Event
import java.time.Instant

class NetworkEventsRepository : EventRepository {

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