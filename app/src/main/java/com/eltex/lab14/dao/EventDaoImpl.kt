package com.eltex.lab14.dao

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.core.content.contentValuesOf
import com.eltex.lab14.data.Event
import com.eltex.lab14.db.EventTable
import com.eltex.lab14.utils.getBooleanOrThrow
import com.eltex.lab14.utils.getLongOrThrow
import com.eltex.lab14.utils.getStringOrThrow

class EventDaoImpl(private val db: SQLiteDatabase) : EventDao {
    override fun getAll(): List<Event> = db.query(
        EventTable.TABLE_NAME,
        EventTable.allColumns,
        null,
        null,
        null,
        null,
        "${EventTable.ID} DESC",
    ).use { cursor ->
        val events = mutableListOf<Event>()

        while (cursor.moveToNext()) {
            events += cursor.readEvent()
        }
        events
    }

    override fun save(event: Event) {
        val contentValues = contentValuesOf(
            EventTable.CONTENT to event.content,
            EventTable.PUBLISHED to event.published,
            EventTable.LIKE_BY_ME to event.likedByMe,
            EventTable.AUTHOR to event.author,
            EventTable.PARTICIPATE_BY_ME to event.participateByMe,
        )
        if (event.id != 0L) {
            contentValues.put(EventTable.ID, event.id)
        }
        val id = db.insert(EventTable.TABLE_NAME, null, contentValues)

        getById(id)
    }

    override fun likeById(id: Long): Event {
        db.execSQL(
            """
                UPDATE ${EventTable.TABLE_NAME} 
                SET ${EventTable.LIKE_BY_ME} = CASE WHEN ${EventTable.LIKE_BY_ME} THEN 0 ELSE 1 END
                WHERE id = ?
            """.trimIndent(), arrayOf(id.toString())
        )
        return getById(id)
    }

    override fun participateById(id: Long): Event {
        db.execSQL(
            """
                UPDATE ${EventTable.TABLE_NAME} 
                SET ${EventTable.PARTICIPATE_BY_ME}  = CASE WHEN ${EventTable.PARTICIPATE_BY_ME} THEN 0 ELSE 1 END
                WHERE id = ?
            """.trimIndent(), arrayOf(id.toString())
        )
        return getById(id)
    }

    override fun deleteById(id: Long) {
        db.delete(
            EventTable.TABLE_NAME, "${EventTable.ID} = ?", arrayOf(id.toString())
        )
    }

    override fun getById(id: Long): Event = db.query(
        EventTable.TABLE_NAME,
        EventTable.allColumns,
        "${EventTable.ID} = ?",
        arrayOf(id.toString()),
        null,
        null,
        null,
    ).use { cursor ->
        cursor.moveToFirst()
        cursor.readEvent()
    }
}

private fun Cursor.readEvent(): Event = Event(
    id = getLongOrThrow(EventTable.ID),
    content = getStringOrThrow(EventTable.CONTENT),
    author = getStringOrThrow(EventTable.AUTHOR),
    published = getStringOrThrow(EventTable.PUBLISHED),
    likedByMe = getBooleanOrThrow(EventTable.LIKE_BY_ME),
    participateByMe = getBooleanOrThrow(EventTable.PARTICIPATE_BY_ME),
)
