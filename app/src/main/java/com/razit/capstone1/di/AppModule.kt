package com.razit.capstone1.di

import com.razit.capstone1.viewmodel.HomeViewModel
import com.razit.core.domain.usecase.FilmInteractor
import com.razit.core.domain.usecase.FilmUseCase
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModule = module {
    viewModel { HomeViewModel(get()) }
}

val useCaseModule = module {
    factory<FilmUseCase> { FilmInteractor(get()) }
}
