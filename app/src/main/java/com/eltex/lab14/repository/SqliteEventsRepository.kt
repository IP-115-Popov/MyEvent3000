package com.eltex.lab14.repository

import com.eltex.lab14.dao.EventDao
import com.eltex.lab14.data.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class SqliteEventsRepository(private val dao: EventDao) : EventRepository {

    private val _state = MutableStateFlow(emptyList<Event>())

    init {
        CoroutineScope(Dispatchers.IO).launch {
            readEvent()
        }
    }

    override fun getEvent(): Flow<List<Event>> = _state.asStateFlow()

    override fun likeById(id: Long) {
        dao.likeById(id)
        sync()
    }

    override fun participateById(id: Long) {
        dao.participateById(id)
        sync()
    }

    override fun addEvent(content: String) {
        dao.save(Event(content = content))
        sync()
    }

    override fun updateEvent(id: Long, content: String) {
        _state.value.find { it.id == id }?.let { dao.save(it) }
        sync()
    }

    override fun deleteById(id: Long) {
        dao.deleteById(id)
        sync()
    }

    private fun sync() {
        _state.update {
            readEvent()
        }
    }

    private fun readEvent() = dao.getAll()
}