package com.eltex.lab14.repository

import android.content.Context
import androidx.core.content.edit
import com.eltex.lab14.data.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class LocalEventsRepository(context: Context) : EventRepository {

    private companion object {
        const val NEXT_ID_KEY = "NEXT_ID_KEY"
        const val POST_KEY = "POST_KEY"
    }

    private val prefs =
        context.applicationContext.getSharedPreferences("events", Context.MODE_PRIVATE)

    var nextId = 100L

    private val _state = MutableStateFlow(readEvent())

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
        sync()
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
        sync()
    }

    override fun addEvent(content: String) {
        _state.update { posts ->
            buildList(capacity = posts.size + 1) {
                add(
                    Event(
                        id = ++nextId,
                        content = content,
                        author = "Student",
                        published = "10.10.10 2024"
                    )
                )
                addAll(posts)
            }
        }
        sync()
    }

    override fun updateEvent(id: Long, content: String) {
        _state.update { posts ->
            posts.map { event ->
                if (event.id == id) {
                    event.copy(content = content)
                } else {
                    event
                }
            }
        }
        sync()
    }

    override fun deleteById(id: Long) {
        _state.update { posts ->
            posts.filter { post ->
                post.id != id
            }
        }
        sync()
    }

    private fun sync() {
        prefs.edit {
            putLong(NEXT_ID_KEY, nextId)
            putString(POST_KEY, Json.encodeToString(_state.value))
        }
    }

    private fun readEvent(): List<Event> {
        val postsSerialized = prefs.getString(POST_KEY, null)
        return if (postsSerialized != null) {
            Json.decodeFromString(postsSerialized)
        } else {
            emptyList()
        }
    }
}