package com.razit.favorite.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.razit.core.BuildConfig
import com.razit.core.domain.usecase.FilmUseCase


class MoviesViewModel(useCase: FilmUseCase):ViewModel() {
    val movies = useCase.getMoviesFavorite(BuildConfig.MOVIES).asLiveData()
    val tvShow = useCase.getMoviesFavorite(BuildConfig.TVSHOW).asLiveData()
}