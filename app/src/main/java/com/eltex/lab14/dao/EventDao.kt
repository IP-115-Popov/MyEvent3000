package com.eltex.lab14.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.eltex.lab14.entity.EventEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface EventDao {
    @Query("SELECT * FROM Events ORDER BY id DESC")
    fun getAll(): Flow<List<EventEntity>>

    @Upsert
    fun save(event: EventEntity)

    @Query(
        """
            UPDATE Events SET likedByMe = CASE WHEN likedByMe THEN 0 ELSE 1 END
            WHERE id = :id
        """
    )
    fun likeById(id: Long)

    @Query(
        """
            UPDATE Events SET participateByMe = CASE WHEN participateByMe THEN 0 ELSE 1 END
            WHERE id = :id
        """
    )
    fun participateById(id: Long)

    @Query("DELETE FROM Events WHERE id = :id")
    fun deleteById(id: Long)

    @Query("SELECT * FROM Events WHERE id = :id")
    fun getById(id: Long): EventEntity

    @Query("UPDATE Events SET content = :content WHERE id = :id")
    fun updateContentEvent(id: Long, content: String)
}