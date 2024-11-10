package com.eltex.lab14.repository

import com.eltex.lab14.data.Post
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class InMemoryPostRepository : PostRepository {

    private  val _state = MutableStateFlow(
         Post(
            author = "Sergey",
            content = "Приглашаю провести уютный вечер за увлекательными играми! У нас есть несколько вариантов настолок, подходящих для любой компании.",
            published = "11.05.22 11:21",
         )
    )
    override fun getPost(): Flow<Post> = _state.asStateFlow()

    override fun like() {
        _state.update {
            it.copy(likedByMe = !it.likedByMe)
        }
    }
}