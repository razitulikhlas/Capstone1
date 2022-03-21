package com.razit.capstone1.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.razit.core.BuildConfig
import com.razit.core.data.source.Resource
import com.razit.core.domain.model.Film
import com.razit.core.domain.usecase.FilmUseCase
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapLatest


class HomeViewModel(private val filmUseCase: FilmUseCase) : ViewModel() {
    val filmMovies = MediatorLiveData<Resource<List<Film>>>()
    val filmTv = MediatorLiveData<Resource<List<Film>>>()

    val queryChannel = BroadcastChannel<String>(Channel.CONFLATED)

    fun getMovies() {
        filmMovies.addSource(filmUseCase.getMovies(BuildConfig.MOVIES).asLiveData()) {
            filmMovies.value = it
        }
    }

    fun getTv() {
        filmTv.addSource(filmUseCase.getTvShow(BuildConfig.TVSHOW).asLiveData()) {
            filmTv.value = it
        }
    }

    val searchResult = queryChannel.asFlow()
        .debounce(300)
        .distinctUntilChanged()
        .mapLatest {
            filmUseCase.searchMovies(it).asLiveData()
        }.asLiveData()

    suspend fun saveToFavorite(filmEntity: Film) = filmUseCase.saveFavoriteMovies(filmEntity)

    suspend fun checkFilmExist(id: Int, type: String): Boolean =
        filmUseCase.checkFilmExist(id, type)

    suspend fun deleteToFavorite(film: Film) = filmUseCase.deleteFavoriteMovies(film)


}
