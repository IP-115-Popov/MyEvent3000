package com.eltex.lab14.repository

import com.eltex.lab14.data.Event
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface EventRepository {
    fun getEvent() : Single<List<Event>> = Single.never()

    fun likeById(id: Long): Single<Event> = Single.never()

    fun deleteLikeById(id: Long): Single<Event> = Single.never()

    fun participateById(id: Long): Single<Event> = Single.never()

    fun deleteParticipateById(id: Long): Single<Event> = Single.never()

    fun save(id: Long, content: String): Single<Event> = Single.never()

    fun deleteById(id: Long): Completable = Completable.never()
}