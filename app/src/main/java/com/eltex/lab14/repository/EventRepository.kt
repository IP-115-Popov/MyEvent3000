package com.eltex.lab14.repository

import com.eltex.lab14.data.Event

interface EventRepository {
    suspend fun getEvent(): List<Event>?

    suspend fun likeById(id: Long): Event

    suspend fun deleteLikeById(id: Long): Event

    suspend fun participateById(id: Long): Event

    suspend fun deleteParticipateById(id: Long): Event

    suspend fun save(id: Long, content: String): Event

    suspend fun deleteById(id: Long)
}