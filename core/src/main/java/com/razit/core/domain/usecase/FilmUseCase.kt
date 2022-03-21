package com.razit.core.domain.usecase

import com.razit.core.data.source.Resource
import com.razit.core.domain.model.Film
import kotlinx.coroutines.flow.Flow

interface FilmUseCase {
    fun getMovies(type:String): Flow<Resource<List<Film>>>
    fun getTvShow(type: String): Flow<Resource<List<Film>>>
    fun searchMovies(query : String): Flow<Resource<List<Film>>>
    suspend fun saveFavoriteMovies(film: Film)
    suspend fun checkFilmExist(id:Int,type:String) : Boolean
    suspend fun deleteFavoriteMovies(film: Film)
    fun getMoviesFavorite(type:String): Flow<List<Film>>
}