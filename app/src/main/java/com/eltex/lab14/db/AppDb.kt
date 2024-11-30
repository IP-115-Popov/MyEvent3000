package com.eltex.lab14.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.eltex.lab14.dao.EventDao
import com.eltex.lab14.entity.EventEntity

@Database(
    entities = [EventEntity::class],
    version = 1,
)
abstract class AppDb : RoomDatabase() {
    abstract val eventDao: EventDao

    companion object {
        @Volatile
        private var INSTANCE: AppDb? = null

        fun getInstance(context: Context): AppDb {
            INSTANCE?.let { return it }

            val application = context.applicationContext

            synchronized(this) {
                INSTANCE?.let { return it }

                val appDb = Room.databaseBuilder(application, AppDb::class.java, "appdb")
                    .createFromAsset("database/appdb.db").fallbackToDestructiveMigration()
                    .allowMainThreadQueries().build()

                INSTANCE = appDb

                return appDb
            }
        }
    }
}