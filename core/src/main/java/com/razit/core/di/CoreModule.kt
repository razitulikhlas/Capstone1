package com.razit.core.di

import androidx.room.Room
import com.razit.core.data.source.local.DatabaseMovies
import com.razit.core.data.source.LocalDataSource
import com.razit.core.data.source.RemoteDataSource
import com.razit.core.data.source.remote.network.ApiClient
import com.razit.core.domain.repository.FilmRepository
import com.razit.core.domain.repository.IFilmRepository
import com.razit.core.utils.AppExecutors
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val retrofitModule = module {
    single { ApiClient.getServices }
}

val remoteDataSourceModule = module {
    factory { RemoteDataSource(get()) }
}

//val repositoryModule = module {
//    factory { AuthRepositoryImpl(get()) }
//    factory { ProductRepositoryImpl(get(), get()) }
//}
//val useCaseModule = module {
//    factory { MyUseCaseProduct(get(), get(), get()) }
//    factory { MyUseCaseAuth(get(), get(), get()) }
//}
//
//val mappingModule = module {
//    factory {
//        MappingData()
//    }
//}


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
//    single { LocalDataSource(get()) }
//    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<IFilmRepository> { FilmRepository(get(), get()) }
}