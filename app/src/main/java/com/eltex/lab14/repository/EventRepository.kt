package com.eltex.lab14.repository

import com.eltex.lab14.data.Post
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    fun getPost(): Flow<Post>

    fun like()

    fun participate()
}