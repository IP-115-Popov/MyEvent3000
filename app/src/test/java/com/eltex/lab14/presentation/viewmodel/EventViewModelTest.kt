package com.eltex.lab14.presentation.viewmodel

import com.eltex.lab14.data.Event
import com.eltex.lab14.repository.EventRepository
import com.eltex.lab14.rx.SchedulersProvider
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Assert.*
import org.junit.Test

class EventViewModelTest {
    @Test
    fun `delete error then error in state`() {
        val error = RuntimeException("test")
        val viewModel = EventViewModel(
            object : EventRepository {
                override fun deleteById(id: Long): Completable =
                    Completable.error(error)
            },
            TestSchedulersProvider
        )
        viewModel.deleteById(1)
        assertEquals(error, viewModel.uiState.value.status.throwableOrNull)
    }
    @Test
    fun `delete success then idle in state`() {
        val status = Status.Idle
        val viewModel = EventViewModel(
            object : EventRepository {
                override fun deleteById(id: Long): Completable =
                    Completable.complete()
            },
            TestSchedulersProvider
        )
        viewModel.deleteById(1)
        assertEquals(status, viewModel.uiState.value.status)
    }


    @Test
    fun `load error then error in state`() {
        val error = RuntimeException("test")
        val viewModel = EventViewModel(
            object : EventRepository {
                override fun getEvent(): Single<List<Event>> =
                    Single.error(error)
            },
            TestSchedulersProvider
        )
        viewModel.load()
        assertEquals(error, viewModel.uiState.value.status.throwableOrNull)
    }
    @Test
    fun `load success then idle in state`() {
        val status = Status.Idle
        val viewModel = EventViewModel(
            object : EventRepository {
                override fun getEvent(): Single<List<Event>> =
                    Single.just(emptyList())
            },
            TestSchedulersProvider
        )
        viewModel.load()
        assertEquals(status, viewModel.uiState.value.status)
    }

    @Test
    fun `likeById error then error in state`() {
        val error = RuntimeException("test")
        val viewModel = EventViewModel(
            object : EventRepository {
                override fun likeById(id: Long): Single<Event> =
                    Single.error(error)
                override fun getEvent(): Single<List<Event>> =
                    Single.just(listOf(Event(id = 1)))
            },
            TestSchedulersProvider
        )
        viewModel.load()
        viewModel.likeById(1)
        assertEquals(error, viewModel.uiState.value.status.throwableOrNull)
    }
    @Test
    fun `likeById success then idle in state`() {
        val status = Status.Idle
        val viewModel = EventViewModel(
            object : EventRepository {
                override fun likeById(id: Long): Single<Event> =
                    Single.just(Event(id = 1))
                override fun getEvent(): Single<List<Event>> =
                    Single.just(listOf(Event(id = 1)))
            },
            TestSchedulersProvider
        )
        viewModel.load()
        viewModel.likeById(1)
        assertEquals(status, viewModel.uiState.value.status)
    }

    @Test
    fun `participateById error then error in state`() {
        val error = RuntimeException("test")
        val viewModel = EventViewModel(
            object : EventRepository {
                override fun deleteParticipateById(id: Long): Single<Event> =
                    Single.error(error)
                override fun getEvent(): Single<List<Event>> =
                    Single.just(listOf(Event(id = 1)))
            },
            TestSchedulersProvider
        )
        viewModel.load()
        viewModel.participateById(1)
        assertEquals(error, viewModel.uiState.value.status.throwableOrNull)
    }
    @Test
    fun `participateById success then idle in state`() {
        val status = Status.Idle
        val viewModel = EventViewModel(
            object : EventRepository {
                override fun participateById(id: Long): Single<Event> =
                    Single.just(Event(id = 1))
                override fun getEvent(): Single<List<Event>> =
                    Single.just(listOf(Event(id = 1)))
            },
            TestSchedulersProvider
        )
        viewModel.load()
        viewModel.participateById(1)
        assertEquals(status, viewModel.uiState.value.status)
    }


}