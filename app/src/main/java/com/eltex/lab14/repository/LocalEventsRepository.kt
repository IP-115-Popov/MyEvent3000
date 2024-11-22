package com.eltex.lab14.repository

import android.content.Context
import androidx.core.content.edit
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.eltex.lab14.data.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class LocalEventsRepository(context: Context) : EventRepository {

    private val applicationContext = context.applicationContext

    private val prefs =
        applicationContext.getSharedPreferences(POST_PREFS_FILE, Context.MODE_PRIVATE)


    //Проблема applicationContext = null
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = POST_DATA_STORE_FILE)

    private var nextId = 100L

    private val _state = MutableStateFlow(emptyList<Event>())

    init {
        CoroutineScope(Dispatchers.IO).launch {
            readEvent()
        }
    }

    private companion object {
        const val NEXT_ID_KEY = "NEXT_ID_KEY"
        const val POST_DATA_STORE_FILE = "POST_DATA_STORE_FILE"
        const val POST_PREFS_FILE = "POST_PREFS_FILE"
        val POST_DATA_STORE = stringPreferencesKey("POST_DATA_STORE")
    }

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
        prefs?.edit {
            putLong(NEXT_ID_KEY, nextId)
        }
        CoroutineScope(Dispatchers.IO).launch {
            applicationContext.dataStore.edit { settings ->
                settings[POST_DATA_STORE] = Json.encodeToString(_state.value)
            }
        }
    }

    private suspend fun readEvent() {
        prefs?.let {
            nextId = it.getLong(NEXT_ID_KEY, 0L)
        }


        applicationContext.dataStore.data.collect { preferences ->
            val serializableEvents = preferences[POST_DATA_STORE]
            val events = if (serializableEvents != null) {
                Json.decodeFromString<List<Event>>(serializableEvents)
            } else {
                emptyList()
            }
            _state.value = events
        }
    }
}