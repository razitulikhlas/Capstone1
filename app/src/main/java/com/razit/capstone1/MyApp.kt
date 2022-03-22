package com.razit.capstone1

import android.app.Application
import com.razit.capstone1.di.useCaseModule
import com.razit.capstone1.di.viewModule
import com.razit.core.diModule.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
            modules(
                listOf(
                    viewModule,
                    retrofitModule,
                    useCaseModule,
                    repositoryModule,
                    remoteDataSourceModule,
                    localDataSourceSourceModule,
                    databaseModule
                )
            )
        }
    }
}