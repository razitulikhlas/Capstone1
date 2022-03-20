package com.razit.core.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FilmEntity::class,FavoriteFilmEntity::class], version = 1,exportSchema = false)
abstract class DatabaseMovies : RoomDatabase() {
    abstract val moviesDao: MoviesDao
}