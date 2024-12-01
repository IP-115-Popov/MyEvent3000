package com.eltex.lab14.repository

import com.eltex.lab14.dao.EventDao
import com.eltex.lab14.data.Event
import com.eltex.lab14.entity.EventEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class SqliteEventsRepository(private val dao: EventDao) : EventRepository {

    override fun getEvent(): Flow<List<Event>> = dao.getAll().map {
        it.map(EventEntity::toEvent)
    }

    override fun likeById(id: Long) {
        dao.likeById(id)
    }

    override fun participateById(id: Long) {
        dao.participateById(id)
    }

    override fun save(id: Long, content: String) {
        dao.save(EventEntity.fromEvent(Event(content = content, id = id)))
    }

    override fun deleteById(id: Long) {
        dao.deleteById(id)
    }

    private fun readEvent() = dao.getAll()
}