package com.eltex.lab14.feature.events.repository

import com.eltex.lab14.feature.events.data.Event

interface EventRepository {

    suspend fun getBefore(id: Long, count: Int): List<Event>

    suspend fun getLatest(count: Int): List<Event>

    suspend fun getEvent(): List<Event>?

    suspend fun likeById(id: Long): Event

    suspend fun deleteLikeById(id: Long): Event

    suspend fun participateById(id: Long): Event

    suspend fun deleteParticipateById(id: Long): Event

    suspend fun save(id: Long, content: String): Event

    suspend fun deleteById(id: Long)
}