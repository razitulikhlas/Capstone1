package com.razit.core.di

import androidx.room.Room
import com.razit.core.BuildConfig
import com.razit.core.data.source.local.DatabaseMovies
import com.razit.core.data.source.LocalDataSource
import com.razit.core.data.source.RemoteDataSource
import com.razit.core.data.source.remote.network.ApiService
import com.razit.core.domain.repository.FilmRepository
import com.razit.core.domain.repository.IFilmRepository
import com.razit.core.utils.SSLCertificateConfigurator
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.net.ssl.X509TrustManager

val retrofitModule = module {
    single {
        val trustManagerFactory = SSLCertificateConfigurator.getTrustManager(androidContext())
        val trustManagers = trustManagerFactory.trustManagers
        if (trustManagers.size != 1 || trustManagers[0] !is X509TrustManager) {
            throw IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers))
        }
        val trustManager = trustManagers[0] as X509TrustManager

        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .sslSocketFactory(SSLCertificateConfigurator.getSSLConfiguration(androidContext()).socketFactory, trustManager)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()

        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client).build().create(ApiService::class.java)
    }
}

val remoteDataSourceModule = module {
    factory { RemoteDataSource(get()) }
}

val databaseModule = module {
    factory {
        get<DatabaseMovies>().moviesDao
    }
    single {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("dicoding".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(androidContext(), DatabaseMovies::class.java, "db_movies")
            .fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}

val localDataSourceSourceModule = module {
    factory { LocalDataSource(get()) }
}

val repositoryModule = module {
    single<IFilmRepository> { FilmRepository(get(), get()) }
}