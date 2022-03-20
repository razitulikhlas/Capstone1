package com.razit.core.utils

import com.razit.core.BuildConfig
import com.razit.core.data.source.local.FavoriteFilmEntity
import com.razit.core.data.source.local.FilmEntity
import com.razit.core.data.source.remote.response.ResultsItem
import com.razit.core.data.source.remote.response.ResultsSearchMovies
import com.razit.core.data.source.remote.response.ResultsTvShow
import com.razit.core.domain.model.Film

object MapperMovies {

    fun mapResponseToFilmEntity(movies: List<ResultsItem>): List<FilmEntity> {
        val filmList = ArrayList<FilmEntity>()
        movies.map {
            val film = FilmEntity(
                id = it.id,
                title = it.title,
                description = it.overview,
                release = it.releaseDate,
                imageUrl = it.posterPath,
                language = it.originalLanguage,
                genre = null,
                rating = it.voteAverage,
                type = BuildConfig.MOVIES
            )
            filmList.add(film)
        }
        return filmList
    }

    fun mapResponseSearchToFilm(movies: List<ResultsSearchMovies>): List<Film> {
        val filmList = ArrayList<Film>()
        movies.map {
            val film = Film(
                id = it.id,
                title = it.title,
                description = it.overview,
                release = it.releaseDate,
                imageUrl = it.posterPath,
                language = it.originalLanguage,
                genre = null,
                rating = it.voteAverage,
                type = BuildConfig.MOVIES
            )
            filmList.add(film)
        }
        return filmList
    }

    fun mapDomainToFavorite(filmEntity: Film): FavoriteFilmEntity {
        return FavoriteFilmEntity(
            id = filmEntity.id,
            title = filmEntity.title,
            description = filmEntity.description,
            imageUrl = filmEntity.imageUrl,
            release = filmEntity.release,
            language = filmEntity.language,
            rating = filmEntity.rating,
            type = filmEntity.type,
            genre = null
        )
    }

    fun mapEntityToDomain(filmEntity: List<FilmEntity>): List<Film> {
        val filmList = ArrayList<Film>()
        filmEntity.map {
            val film = Film(
                id = it.id,
                title = it.title,
                description = it.description,
                release = it.release,
                imageUrl = it.imageUrl,
                language = it.language,
                genre = null,
                rating = it.rating,
                type = it.type
            )
            filmList.add(film)
        }
        return filmList
    }

    fun mapFavoriteEntityToDomain(filmEntity: List<FavoriteFilmEntity>): List<Film> {
        val filmList = ArrayList<Film>()
        filmEntity.map {
            val film = Film(
                id = it.id,
                title = it.title,
                description = it.description,
                release = it.release,
                imageUrl = it.imageUrl,
                language = it.language,
                genre = null,
                rating = it.rating,
                type = it.type
            )
            filmList.add(film)
        }
        return filmList
    }

    fun mapsResponseTvShowToFilmEntity(tvShow: List<ResultsTvShow>): List<FilmEntity> {
        val filmList = ArrayList<FilmEntity>()
        tvShow.map {
            val film = FilmEntity(
                id = it.id,
                title = it.name,
                description = it.overview,
                release = it.firstAirDate,
                imageUrl = it.posterPath,
                language = it.originalLanguage,
                genre = null,
                rating = it.voteAverage,
                type = BuildConfig.TVSHOW
            )
            filmList.add(film)
        }
        return filmList
    }


}