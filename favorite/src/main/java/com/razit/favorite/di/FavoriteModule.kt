package com.razit.favorite.di

import com.razit.favorite.viewmodel.MoviesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModuleFavorite = module {
    viewModel { MoviesViewModel(get()) }
}