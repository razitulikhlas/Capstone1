package com.razit.core.data.source

import android.util.Log
import com.razit.core.data.source.remote.network.ApiResponse
import com.razit.core.data.source.remote.network.ApiService
import com.razit.core.data.source.remote.response.ResultsItem
import com.razit.core.data.source.remote.response.ResultsSearchMovies
import com.razit.core.data.source.remote.response.ResultsTvShow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {

    suspend fun getMovies(): Flow<ApiResponse<List<ResultsItem>>> {
        return flow {
            try {
                val response = apiService.getMoviesPage()
                val dataArray = response.results
                if (dataArray!!.isNotEmpty()) {
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getTvShow(): Flow<ApiResponse<List<ResultsTvShow>>> {
        return flow {
            try {
                val response = apiService.getTvShowPage()
                val dataArray = response.results
                if (dataArray!!.isNotEmpty()) {
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun searchMovies(query: String): Flow<ApiResponse<List<ResultsSearchMovies>>> {
        return flow {
            try {
                val response = apiService.searchMovies(query = query)
                val dataArray = response.results
                if (dataArray!!.isNotEmpty()) {
                    emit(ApiResponse.Success(dataArray))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

}