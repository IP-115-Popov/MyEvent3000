package com.eltex.lab14.presentation.viewmodel

import com.eltex.lab14.data.Event
import com.eltex.lab14.repository.EventRepository

interface TestEventRepository: EventRepository {
    override suspend fun getEvent(): List<Event>? = error("Not mocked")

    override suspend fun likeById(id: Long): Event = error("Not mocked")

    override suspend fun deleteLikeById(id: Long): Event = error("Not mocked")

    override suspend fun participateById(id: Long): Event = error("Not mocked")

    override suspend fun deleteParticipateById(id: Long): Event = error("Not mocked")

    override suspend fun save(id: Long, content: String): Event = error("Not mocked")

    override suspend fun deleteById(id: Long): Unit = error("Not mocked")
}