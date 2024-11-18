package com.eltex.lab14.repository

import com.eltex.lab14.data.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class InMemoryEventRepository : EventRepository {

    var nextId = 100L

    private val _state = MutableStateFlow(List(5) {
        Event(
            id = it.toLong() + 1L,
            author = "Sergey",
            content = "#$it Приглашаю провести уютный вечер за увлекательными играми! У нас есть несколько вариантов настолок, подходящих для любой компании.",
            published = "11.05.22 11:21",
        )
    } + List(5) {
        Event(
            id = it.toLong() + 1L,
            author = "Sergey",
            content = "#$it Приглашаю провести уютный вечер за увлекательными играми! У нас есть несколько вариантов настолок, подходящих для любой компании.",
            published = "22.11.22 11:21",
        )
    })

    override fun getEvent(): Flow<List<Event>> = _state.asStateFlow()

    override fun likeById(id: Long) {
        _state.update {
            it.map { event ->
                if (event.id == id) {
                    event.copy(likedByMe = !event.likedByMe)
                } else {
                    event
                }
            }
        }
    }

    override fun participateById(id: Long) {
        _state.update {
            it.map { event ->
                if (event.id == id) {
                    event.copy(participateByMe = !event.participateByMe)
                } else {
                    event
                }
            }
        }
    }

    override fun addEvent(content: String) {
        _state.update { posts ->
            buildList(capacity = posts.size + 1) {
                add(
                    Event(
                        id = ++nextId, content = content, author = "Student"
                    )
                )
                addAll(posts)
            }
        }
    }
}