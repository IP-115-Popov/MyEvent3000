package com.eltex.lab14.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.eltex.lab14.data.Event

@Entity(tableName = "Events")
data class EventEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo("id") val id: Long = 0L,

    @ColumnInfo("author") val author: String = "",

    @ColumnInfo("content") val content: String = "",

    @ColumnInfo("published") val published: String = "",

    @ColumnInfo("likedByMe") val likedByMe: Boolean = false,

    @ColumnInfo("participateByMe") val participateByMe: Boolean = false,
) {
    companion object {
        fun fromEvent(event: Event) = with(event) {
            EventEntity(
                id = id,
                author = author,
                content = content,
                published = published,
                likedByMe = likedByMe,
                participateByMe = participateByMe
            )
        }
    }

    fun toEvent() = Event(
        id = id,
        author = author,
        content = content,
        published = published,
        likedByMe = likedByMe,
        participateByMe = participateByMe
    )
}