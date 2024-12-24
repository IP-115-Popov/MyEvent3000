package com.eltex.lab14.repository

import com.eltex.lab14.api.EventApi
import com.eltex.lab14.data.Event
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import java.time.Instant

class NetworkEventsRepository : EventRepository {

    override fun getEvent() = EventApi.INSTANCE.getAll()

    override fun likeById(id: Long): Single<Event> = EventApi.INSTANCE.likeById(id)

    override fun deleteLikeById(id: Long): Single<Event> = EventApi.INSTANCE.deleteLikeById(id)

    override fun participateById(id: Long): Single<Event> = EventApi.INSTANCE.participateById(id)

    override fun deleteParticipateById(id: Long): Single<Event> = EventApi.INSTANCE.deleteParticipateById(id)

    override fun save(id: Long, content: String): Single<Event> = EventApi.INSTANCE.savEvent(
        Event(
            id = id,
            content = content,
            datetime = Instant.now(),
        )
    )

    override fun deleteById(id: Long): Completable = EventApi.INSTANCE.deleteById(id)

}