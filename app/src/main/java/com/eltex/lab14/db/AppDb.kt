package com.eltex.lab14.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.eltex.lab14.dao.EventDao
import com.eltex.lab14.entity.EventEntity

@Database(
    entities = [EventEntity::class],
    version = 2,
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
                    .addMigrations(MIGRATION_1_2)
                    //.fallbackToDestructiveMigration()
                    .allowMainThreadQueries().build()

                INSTANCE = appDb

                return appDb
            }
        }
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Events ADD COLUMN aboba INTEGER NOT NULL DEFAULT 0")
            }
        }
    }
}