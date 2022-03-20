package com.razit.core.domain.usecase

import com.razit.core.data.source.Resource
import com.razit.core.domain.model.Film
import com.razit.core.domain.repository.IFilmRepository
import kotlinx.coroutines.flow.Flow

class FilmInteractor(private val repository: IFilmRepository):FilmUseCase {
    override fun getMovies(type: String): Flow<Resource<List<Film>>> = repository.getMovies(type)
    override fun getTvShow(type: String): Flow<Resource<List<Film>>> = repository.getTvShow(type)
    override fun searchMovies(query: String): Flow<Resource<List<Film>>> = repository.searchMovies(query)
    override fun saveFavoriteMovies(film: Film) = repository.saveFavoriteMovies(film)
    override fun checkFilmExist(id: Int, type: String): Boolean = repository.checkFilmExist(id,type)
    override fun deleteFavoriteMovies(film: Film) = repository.deleteFavoriteMovies(film)
    override fun getMoviesFavorite(type: String): Flow<List<Film>> = repository.getMoviesFavorite(type)
}