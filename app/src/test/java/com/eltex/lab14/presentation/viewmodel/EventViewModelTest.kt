package com.eltex.lab14.presentation.viewmodel

import com.eltex.lab14.feature.events.data.Event
import com.eltex.lab14.feature.events.viewmodel.EventUiState
import com.eltex.lab14.feature.events.viewmodel.EventViewModel
import com.eltex.lab14.feature.events.ui.EventUiModelMapper
import com.eltex.lab14.util.Status
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import java.lang.Thread.sleep

class EventViewModelTest {

    @get:Rule
    val coroutinesRule: TestCoroutineRule = TestCoroutineRule()

    @Test
    fun `delete error then error in state`() {
        // arrange
        val error = RuntimeException("test")
        val excepted = EventUiState(
            events = null,
            status = Status.Error(error)
        )
        val viewModel = EventViewModel(
            object : TestEventRepository {
                override suspend fun deleteById(id: Long) = throw error
            }
        )
        // act
        viewModel.deleteById(1)
        val actual = viewModel.uiState.value
        // assert
        assertEquals(excepted, actual)
    }

    @Test
    fun `delete success then idle in state`() {
        // arrange
        val excepted = EventUiState(
            events = null,
            status = Status.Idle
        )
        val viewModel = EventViewModel(
            object : TestEventRepository {
                override suspend fun deleteById(id: Long): Unit {}
                override suspend fun getEvent(): List<Event>? = null
            }
        )
        // act
        viewModel.deleteById(1)
        val actual = viewModel.uiState.value
        // assert
        assertEquals(excepted, actual)
    }


    @Test
    fun `load error then error in state`() {
        // arrange
        val error = RuntimeException("test")
        val excepted = EventUiState(
            events = null,
            status = Status.Error(error)
        )
        val viewModel = EventViewModel(
            object : TestEventRepository {
                override suspend fun getEvent() = throw error
            }
        )
        // act
        viewModel.load()
        val actual = viewModel.uiState.value
        // assert
        assertEquals(excepted, actual)
    }

    @Test
    fun `load success then idle in state`() {
        // arrange
        val mapper = EventUiModelMapper()
        val events = listOf(Event())
        val excepted = EventUiState(
            events = events.map {mapper.map(it)  },
            status = Status.Idle
        )
        val viewModel = EventViewModel(
            object : TestEventRepository {
                override suspend fun getEvent() = events
            }
        )
        // act
        viewModel.load()
        sleep(1000)
        val actual = viewModel.uiState.value
        // assert
        assertEquals(excepted, actual)
    }

    @Test
    fun `likeById error then error in state`() {
        // arrange
        val mapper = EventUiModelMapper()
        val error = RuntimeException("test")
        val events = listOf(Event(id = 1))
        val excepted = EventUiState(
            events = events.map { mapper.map(it) },
            status = Status.Error(error)
        )
        val viewModel = EventViewModel(
            object : TestEventRepository {
                override suspend fun likeById(id: Long): Event = throw error
                override suspend fun getEvent(): List<Event> = events
            }
        )
        // act
        viewModel.load()
        sleep(1000)
        viewModel.likeById(1)
        sleep(1000)
        val actual = viewModel.uiState.value
        // assert
        assertEquals(excepted, actual)
    }

    @Test
    fun `likeById success then idle in state`() {
        // arrange
        val mapper = EventUiModelMapper()
        val events = listOf(Event(id = 1))
        val excepted = EventUiState(
            events = events.map { mapper.map(it) },
            status = Status.Idle
        )
        val viewModel = EventViewModel(
            object : TestEventRepository {
                override suspend fun likeById(id: Long): Event = events.first()
                override suspend fun getEvent(): List<Event> = events
            }
        )
        // act
        viewModel.load()
        sleep(1000)
        viewModel.likeById(1)
        sleep(1000)
        val actual = viewModel.uiState.value
        // assert
        assertEquals(excepted, actual)
    }

    @Test
    fun `participateById error then error in state`() {
        // arrange
        val mapper = EventUiModelMapper()
        val error = RuntimeException("test")
        val events = listOf(Event(id = 1))
        val excepted = EventUiState(
            events = events.map { mapper.map(it) },
            status = Status.Error(error)
        )
        val viewModel = EventViewModel(
            object : TestEventRepository {
                override suspend fun participateById(id: Long): Event = throw error

                override suspend fun getEvent(): List<Event> = events
            }
        )
        // act
        viewModel.load()
        sleep(1000)
        viewModel.participateById(1)
        sleep(1000)
        val actual = viewModel.uiState.value
        // assert
        assertEquals(excepted, actual)
    }

    @Test
    fun `participateById success then idle in state`() {
        // arrange
        val mapper = EventUiModelMapper()
        val error = RuntimeException("test")
        val events = listOf(Event(id = 1))
        val excepted = EventUiState(
            events = events.map { mapper.map(it) },
            status = Status.Idle
        )
        val viewModel = EventViewModel(
            object : TestEventRepository {
                override suspend fun participateById(id: Long): Event = events.first()

                override suspend fun getEvent(): List<Event> = events

            }
        )
        // act
        viewModel.load()
        sleep(1000)
        viewModel.participateById(1)
        sleep(1000)
        val actual = viewModel.uiState.value
        // assert
        assertEquals(excepted, actual)
    }


}