package com.mego.myapplication.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mego.myapplication.models.News

@Database(
    entities = [News::class],
    version = 1
)
@TypeConverters(Converters::class)

abstract class NewsDB() : RoomDatabase() {

    abstract fun getArticleDao(): NewsDAO

    companion object {
        @Volatile
        private var instance: NewsDB? = null
        private val LOCK = Any()

    operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                NewsDB::class.java,
                "news_db.db"
            ).build()
    }
}

