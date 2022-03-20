package com.razit.capstone1.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.razit.core.BuildConfig
import com.razit.core.domain.model.Film
import com.razit.core.domain.usecase.FilmUseCase
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapLatest


class HomeViewModel(private val filmUseCase: FilmUseCase) : ViewModel() {


    val queryChannel = BroadcastChannel<String>(Channel.CONFLATED)

    val filmMovies = filmUseCase.getMovies(BuildConfig.MOVIES).asLiveData()
    val filmTv = filmUseCase.getTvShow(BuildConfig.TVSHOW).asLiveData()


    val searchResult = queryChannel.asFlow()
        .debounce(300)
        .distinctUntilChanged()
        .mapLatest {
            filmUseCase.searchMovies(it).asLiveData()
        }.asLiveData()

    fun saveToFavorite(filmEntity: Film) = filmUseCase.saveFavoriteMovies(filmEntity)

    fun checkFilmExist(id: Int, type: String): Boolean = filmUseCase.checkFilmExist(id, type)

    fun deleteToFavorite(film: Film) = filmUseCase.deleteFavoriteMovies(film)


}
