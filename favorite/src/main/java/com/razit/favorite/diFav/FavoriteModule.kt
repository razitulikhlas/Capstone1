package com.razit.favorite.diFav

import com.razit.favorite.viewmodelFav.MoviesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModuleFavorite = module {
    viewModel { MoviesViewModel(get()) }
}