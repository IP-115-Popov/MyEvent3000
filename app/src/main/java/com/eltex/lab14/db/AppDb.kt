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
    version = 3,
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
                    .createFromAsset("database/init.db")
                    .fallbackToDestructiveMigration()
                    .addMigrations(MIGRATION_1_2).addMigrations(MIGRATION_2_3)
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
        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    """
                            CREATE TABLE Events_temp (
                                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                                author TEXT NOT NULL,
                                content TEXT NOT NULL,
                                published TEXT NOT NULL,
                                likedByMe INTEGER NOT NULL,
                                participateByMe INTEGER NOT NULL
                            )
                        """
                )
                database.execSQL(
                    """
                            INSERT INTO Events_temp (id, author, content, published, likedByMe, participateByMe)
                            SELECT id, author, content, published, likedByMe, participateByMe FROM Events
                        """
                )

                database.execSQL("DROP TABLE Events")
                database.execSQL("ALTER TABLE Events_temp RENAME TO Events")
            }
        }
    }
}