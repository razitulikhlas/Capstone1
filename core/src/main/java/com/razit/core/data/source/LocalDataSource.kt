package com.razit.core.data.source

import android.util.Log
import com.razit.core.data.source.local.FavoriteFilmEntity
import com.razit.core.data.source.local.FilmEntity
import com.razit.core.data.source.local.MoviesDao
import com.razit.core.utils.AppExecutors
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class LocalDataSource(private val moviesDao: MoviesDao) {

    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    fun getAllFilmType(type: String): Flow<List<FilmEntity>> = moviesDao.getAllFilmByType(type)

    fun getMoviesFavorite(type: String): Flow<List<FavoriteFilmEntity>> = moviesDao.getFavorite(type)

    suspend fun insertFilm(list: List<FilmEntity>) = moviesDao.insertAll(list)

    fun saveFavorite(filmEntity: FavoriteFilmEntity) =executorService.submit(Callable {
        moviesDao.insertFavorite(filmEntity)
    })

    fun deleteFavorite(filmEntity: FavoriteFilmEntity) =executorService.submit(Callable {
        moviesDao.deleteFavorite(filmEntity)
    })

    fun checkFilmExist(id :Int,type:String) : Boolean = executorService.submit(Callable {
        moviesDao.checkFilmExist(id,type)
    }).get()
}