package com.eltex.lab14.feature.events.repository

import android.content.ContentResolver
import android.content.Context
import com.eltex.lab14.feature.events.api.EventApi
import com.eltex.lab14.feature.events.data.AttachmentType
import com.eltex.lab14.feature.events.data.Event
import com.eltex.lab14.feature.media.Attachment
import com.eltex.lab14.feature.media.MediaApi
import com.eltex.lab14.feature.media.MediaDto
import com.eltex.lab14.feature.newevent.viewmodel.FileModel
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.time.Instant
import javax.inject.Inject

class NetworkEventsRepository @Inject constructor(
    private val contextResolver: ContentResolver,
    private val eventApi: EventApi,
    private val mediaApi: MediaApi
) : EventRepository {
    override suspend fun getBefore(id: Long, count: Int): List<Event> =
        eventApi.getBefore(id, count)

    override suspend fun getLatest(count: Int): List<Event> = eventApi.getLatest(count)

    override suspend fun getEvent() = eventApi.getAll()

    override suspend fun likeById(id: Long): Event = eventApi.likeById(id)

    override suspend fun deleteLikeById(id: Long): Event = eventApi.deleteLikeById(id)

    override suspend fun participateById(id: Long): Event = eventApi.participateById(id)

    override suspend fun deleteParticipateById(id: Long): Event =
        eventApi.deleteParticipateById(id)

    override suspend fun save(id: Long, content: String,  fileModel: FileModel?): Event {
        val event = fileModel?.let {
            val media = uploadMedia(it)
            Event(
                id = id,
                content = content,
                datetime = Instant.now(),
                attachment = Attachment(media.url, AttachmentType.IMAGE)
            )
        } ?: Event(
                id = id,
                content = content,
                datetime = Instant.now(),
            )

        return eventApi.savEvent(event)
    }

    override suspend fun deleteById(id: Long) = eventApi.deleteById(id)

    private suspend fun uploadMedia(fileModel: FileModel): MediaDto =
        mediaApi.uploadMedia(
            MultipartBody.Part.createFormData(
                "file",
                "file",
                requireNotNull(contextResolver.openInputStream(fileModel.uri)).use {
                    it.readBytes()
                }
                    .toRequestBody()
            )
        )

}