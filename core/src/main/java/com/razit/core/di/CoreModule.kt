package com.razit.core.di

import androidx.room.Room
import com.razit.core.data.source.local.DatabaseMovies
import com.razit.core.data.source.LocalDataSource
import com.razit.core.data.source.RemoteDataSource
import com.razit.core.data.source.remote.network.ApiClient
import com.razit.core.domain.repository.FilmRepository
import com.razit.core.domain.repository.IFilmRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val retrofitModule = module {
    single { ApiClient.getServices }
}

val remoteDataSourceModule = module {
    factory { RemoteDataSource(get()) }
}

val databaseModule = module {
    factory {
        get<DatabaseMovies>().moviesDao
    }
    single {
        Room.databaseBuilder(androidContext(), DatabaseMovies::class.java, "db_movies")
            .fallbackToDestructiveMigration()
            .build()
    }
}

val localDataSourceSourceModule = module {
    factory { LocalDataSource(get()) }
}

val repositoryModule = module {
    single<IFilmRepository> { FilmRepository(get(), get()) }
}