package com.eltex.lab14.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(context: Context) : SQLiteOpenHelper(context, "eventdb", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
                CREATE TABLE ${EventTable.TABLE_NAME} (
                ${EventTable.ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                ${EventTable.CONTENT} TEXT NOT NULL,
                ${EventTable.AUTHOR} TEXT NOT NULL,
                ${EventTable.PUBLISHED} TEXT NOT NULL,
                ${EventTable.LIKE_BY_ME} INTEGER NOT NULL DEFAULT 0,
                ${EventTable.PARTICIPATE_BY_ME} INTEGER NOT NULL DEFAULT 0
                );
            """.trimIndent()
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}