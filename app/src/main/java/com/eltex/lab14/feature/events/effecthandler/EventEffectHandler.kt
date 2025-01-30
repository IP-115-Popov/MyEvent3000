package com.eltex.lab14.feature.events.effecthandler

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.eltex.lab14.feature.events.model.EventWithError
import com.eltex.lab14.feature.events.repository.EventRepository
import com.eltex.lab14.feature.events.ui.EventUiModelMapper
import com.eltex.lab14.feature.events.viewmodel.EventEffect
import com.eltex.lab14.feature.events.viewmodel.EventMessage
import com.eltex.lab14.mvi.EffectHandler
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.merge
import javax.inject.Inject

class EventEffectHandler @Inject constructor(
    private val repository: EventRepository,
    private val eventMapper: EventUiModelMapper,
) : EffectHandler<EventEffect, EventMessage> {
    override fun connect(effects: Flow<EventEffect>): Flow<EventMessage> = listOf(
        handleNextPage(effects),
        handleInitialPage(effects),
        handleDelete(effects),
        handleLike(effects),
        handleParticipate(effects)
    ).merge()

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun handleNextPage(effects: Flow<EventEffect>) =
        effects.filterIsInstance<EventEffect.LoadNextPage>().mapLatest {
            EventMessage.NextPageLoaded(
                try {
                    repository.getBefore(it.id, it.count).map(eventMapper::map).right()
                } catch (e: Exception) {
                    if (e is CancellationException) throw e
                    e.left()
                }
            )
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun handleInitialPage(effects: Flow<EventEffect>) =
        effects.filterIsInstance<EventEffect.LoadInitialPage>().mapLatest {
            EventMessage.InitialLoaded(
                try {
                    repository.getLatest(it.count).map(eventMapper::map).right()
                } catch (e: Exception) {
                    if (e is CancellationException) throw e
                    e.left()
                }
            )
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun handleDelete(effects: Flow<EventEffect>) =
        effects.filterIsInstance<EventEffect.Delete>().mapLatest {
            try {
                repository.deleteById(it.event.id)
            } catch (e: Exception) {
                if (e is CancellationException) throw e
                EventMessage.DeleteError(EventWithError(it.event, e))
            }
        }.filterIsInstance<EventMessage.DeleteError>()

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun handleLike(effects: Flow<EventEffect>) =
        effects.filterIsInstance<EventEffect.Like>().mapLatest {
            EventMessage.LikeResult(
                try {
                    Either.Right(
                        eventMapper.map(
                            if (it.event.likedByMe) {
                                repository.deleteLikeById(it.event.id)
                            } else {
                                repository.likeById(it.event.id)
                            }
                        )
                    )
                } catch (e: Exception) {
                    if (e is CancellationException) throw e
                    Either.Left(EventWithError(it.event, e))
                }
            )
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun handleParticipate(effects: Flow<EventEffect>) =
        effects.filterIsInstance<EventEffect.Participate>().mapLatest {
            EventMessage.ParticipateResult(
                try {
                    Either.Right(
                        eventMapper.map(
                            if (it.event.participateByMe) {
                                repository.deleteParticipateById(it.event.id)
                            } else {
                                repository.participateById(it.event.id)
                            }
                        )
                    )
                } catch (e: Exception) {
                    if (e is CancellationException) throw e
                    Either.Left(EventWithError(it.event, e))
                }
            )
        }
}