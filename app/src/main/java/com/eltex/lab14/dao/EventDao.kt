package com.eltex.lab14.dao

import com.eltex.lab14.data.Event

interface EventDao {
    fun getAll(): List<Event>
    fun save(event: Event)
    fun likeById(id: Long): Event
    fun participateById(id: Long): Event
    fun deleteById(id: Long)
    fun getById(id: Long): Event
}