package com.eltex.lab14.repository

import com.eltex.lab14.data.Event
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    fun getPost(): Flow<List<Event>>

    fun likeById(id: Long)

    fun participateById(id: Long)
}