package com.eltex.lab14.repository

import com.eltex.lab14.data.Event
import com.eltex.lab14.util.Callback

interface EventRepository {
    fun getEvent(callback: Callback<List<Event>>)

    fun likeById(id: Long, callback: Callback<Event>)

    fun participateById(id: Long, callback: Callback<Event>)

    fun save(id: Long, content: String, callback: Callback<Event>)

    fun deleteById(id: Long, callback: Callback<Unit>)
}