package com.razit.favorites.diFav

import com.razit.favorites.viewModelFav.MoviesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModuleFavorite = module {
    viewModel { MoviesViewModel(get()) }
}