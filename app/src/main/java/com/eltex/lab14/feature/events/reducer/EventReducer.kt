package com.eltex.lab14.feature.events.reducer

import arrow.core.Either
import com.eltex.lab14.feature.events.viewmodel.EventEffect
import com.eltex.lab14.feature.events.viewmodel.EventMessage
import com.eltex.lab14.feature.events.viewmodel.EventStatus
import com.eltex.lab14.feature.events.viewmodel.EventUiState
import com.eltex.lab14.mvi.Reducer
import com.eltex.lab14.mvi.ReducerResult
import javax.inject.Inject

class EventReducer @Inject constructor() : Reducer<EventUiState, EventEffect, EventMessage> {
    private companion object {
        const val PAGE_SIZE = 10
    }

    override fun reduce(
        old: EventUiState, message: EventMessage
    ): ReducerResult<EventUiState, EventEffect> = when (message) {
        is EventMessage.Delete -> ReducerResult(
            old.copy(events = old.events.filter { it.id != message.event.id }),
            EventEffect.Delete(message.event)
        )

        is EventMessage.DeleteError -> ReducerResult(
            old.copy(events = buildList(capacity = old.events.size + 1) {
                val post = message.error.event
                addAll(old.events.filter { it.id > post.id })
                add(post)
                addAll(old.events.filter { it.id < post.id })
            }),
        )

        EventMessage.HandleError -> ReducerResult(
            old.copy(singleError = null)
        )

        is EventMessage.InitialLoaded -> ReducerResult(
            when (val messageResult = message.result) {
                is Either.Left -> {
                    if (old.events.isNotEmpty()) {
                        old.copy(singleError = messageResult.value, status = EventStatus.Idle())
                    } else {
                        old.copy(status = EventStatus.EmptyError(messageResult.value))
                    }
                }

                is Either.Right -> old.copy(
                    events = messageResult.value, status = EventStatus.Idle()
                )
            }
        )

        is EventMessage.Like -> ReducerResult(old.copy(events = old.events.map {
            if (it.id == message.event.id) {
                it.copy(
                    likedByMe = !it.likedByMe,
                    likes = if (it.likedByMe) it.likes - 1 else it.likes + 1
                )
            } else {
                it
            }
        }))

        is EventMessage.LikeResult -> ReducerResult(
            when (val messageResult = message.result) {
                is Either.Left -> {
                    val value = messageResult.value
                    val event = value.event
                    old.copy(
                        events = old.events.map {
                            if (it.id == event.id) {
                                event
                            } else {
                                it
                            }
                        }, singleError = value.throwable
                    )
                }

                is Either.Right -> {
                    val event = messageResult.value
                    old.copy(events = old.events.map {
                        if (it.id == event.id) {
                            event
                        } else {
                            it
                        }
                    })
                }
            }
        )

        EventMessage.LoadNextPage -> {
            val loadingFinished = (old.status as? EventStatus.Idle)?.loadingFinished == true
            val effect = if (loadingFinished) {
                null
            } else {
                EventEffect.LoadNextPage(old.events.last().id, PAGE_SIZE)
            }

            val status = if (loadingFinished) {
                old.status
            } else {
                EventStatus.NextPageLoading
            }

            ReducerResult(
                old.copy(status = status), effect
            )
        }

        is EventMessage.NextPageLoaded -> ReducerResult(
            when (val messageResult = message.result) {
                is Either.Left -> {
                    old.copy(status = EventStatus.NextPageError(messageResult.value))
                }

                is Either.Right -> {
                    val eventUiModels = messageResult.value
                    val loadingFinished = eventUiModels.size < PAGE_SIZE
                    old.copy(
                        events = old.events + messageResult.value,
                        status = EventStatus.Idle(loadingFinished)
                    )
                }
            }
        )

        EventMessage.Refresh -> ReducerResult(
            old.copy(
                status = if (old.events.isNotEmpty()) {
                    EventStatus.Refreshing
                } else {
                    EventStatus.EmptyLoading
                }
            ),
            EventEffect.LoadInitialPage(PAGE_SIZE),
        )

        is EventMessage.Participate -> ReducerResult(old.copy(events = old.events.map {
            if (it.id == message.event.id) {
                it.copy(
                    participateByMe = !it.participateByMe
                )
            } else {
                it
            }
        }))

        is EventMessage.ParticipateResult -> ReducerResult(
            when (val messageResult = message.result) {
                is Either.Left -> {
                    val value = messageResult.value
                    val event = value.event
                    old.copy(
                        events = old.events.map {
                            if (it.id == event.id) {
                                event
                            } else {
                                it
                            }
                        }, singleError = value.throwable
                    )
                }

                is Either.Right -> {
                    val event = messageResult.value
                    old.copy(events = old.events.map {
                        if (it.id == event.id) {
                            event
                        } else {
                            it
                        }
                    })
                }
            }
        )
    }
}