package com.eltex.lab14.db

object EventTable {
    const val TABLE_NAME = "EVENTS"
    const val ID = "id"
    const val CONTENT = "content"
    const val AUTHOR = "author"
    const val PUBLISHED = "published"
    const val LIKE_BY_ME = "likeByMe"
    const val PARTICIPATE_BY_ME = "participateByMe"

    val allColumns = arrayOf(ID, CONTENT, AUTHOR, PUBLISHED, LIKE_BY_ME, PARTICIPATE_BY_ME)
}