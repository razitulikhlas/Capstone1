package com.razit.core.data.source

import com.razit.core.data.source.local.FavoriteFilmEntity
import com.razit.core.data.source.local.FilmEntity
import com.razit.core.data.source.local.MoviesDao
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class LocalDataSource(private val moviesDao: MoviesDao) {

    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    fun getAllFilmType(type: String): Flow<List<FilmEntity>> = moviesDao.getAllFilmByType(type)

    fun getMoviesFavorite(type: String): Flow<List<FavoriteFilmEntity>> =
        moviesDao.getFavorite(type)

    suspend fun insertFilm(list: List<FilmEntity>) = moviesDao.insertAll(list)

    suspend fun saveFavorite(filmEntity: FavoriteFilmEntity) = moviesDao.insertFavorite(filmEntity)

    suspend fun deleteFavorite(filmEntity: FavoriteFilmEntity) =
        moviesDao.deleteFavorite(filmEntity)

    suspend fun checkFilmExist(id: Int, type: String): Boolean = moviesDao.checkFilmExist(id, type)
}