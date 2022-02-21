package com.mjafarshidik.moviecatalogue.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mjafarshidik.moviecatalogue.data.source.local.entity.MoviesEntity
import com.mjafarshidik.moviecatalogue.data.source.local.entity.TVShowsEntity

@Database(
    entities = [MoviesEntity::class, TVShowsEntity::class],
    version = 1,
    exportSchema = false
)
abstract class DatabaseDAO : RoomDatabase() {
    abstract fun dataDao(): DataDAO

    companion object {
        @Volatile
        private var INSTANCE: DatabaseDAO? = null

        fun getInstance(context: Context): DatabaseDAO =
            INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseDAO::class.java,
                    "db_movie_catalogue.db"
                ).build().apply {
                    INSTANCE = this
                }
            }
    }
}