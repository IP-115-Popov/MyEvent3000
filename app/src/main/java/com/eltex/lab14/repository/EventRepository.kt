package com.eltex.lab14.repository

import com.eltex.lab14.data.Event
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    fun getEvent(): Flow<List<Event>>

    fun likeById(id: Long)

    fun participateById(id: Long)

    fun addEvent(content : String)

    fun updateEvent(id: Long, content : String)

    fun deleteById(id: Long)
}