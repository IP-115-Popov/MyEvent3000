package com.eltex.lab14.feature.events.repository

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

class NetworkEventsRepository(
    private val context: Context
) : EventRepository {
    override suspend fun getBefore(id: Long, count: Int): List<Event> =
        EventApi.INSTANCE.getBefore(id, count)

    override suspend fun getLatest(count: Int): List<Event> = EventApi.INSTANCE.getLatest(count)

    override suspend fun getEvent() = EventApi.INSTANCE.getAll()

    override suspend fun likeById(id: Long): Event = EventApi.INSTANCE.likeById(id)

    override suspend fun deleteLikeById(id: Long): Event = EventApi.INSTANCE.deleteLikeById(id)

    override suspend fun participateById(id: Long): Event = EventApi.INSTANCE.participateById(id)

    override suspend fun deleteParticipateById(id: Long): Event =
        EventApi.INSTANCE.deleteParticipateById(id)

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

        return EventApi.INSTANCE.savEvent(event)
    }

    override suspend fun deleteById(id: Long) = EventApi.INSTANCE.deleteById(id)

    private suspend fun uploadMedia(fileModel: FileModel): MediaDto =
        MediaApi.INSTANCE.uploadMedia(
            MultipartBody.Part.createFormData(
                "file",
                "file",
                requireNotNull(context.contentResolver.openInputStream(fileModel.uri)).use {
                    it.readBytes()
                }
                    .toRequestBody()
            )
        )

}