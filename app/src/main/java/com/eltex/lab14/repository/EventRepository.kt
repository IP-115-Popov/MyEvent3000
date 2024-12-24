package com.eltex.lab14.repository

import com.eltex.lab14.data.Event
import com.eltex.lab14.util.Callback
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface EventRepository {
    fun getEvent() : Single<List<Event>>

    fun likeById(id: Long): Single<Event>

    fun deleteLikeById(id: Long): Single<Event>

    fun participateById(id: Long): Single<Event>

    fun deleteParticipateById(id: Long): Single<Event>

    fun save(id: Long, content: String): Single<Event>

    fun deleteById(id: Long): Completable
}