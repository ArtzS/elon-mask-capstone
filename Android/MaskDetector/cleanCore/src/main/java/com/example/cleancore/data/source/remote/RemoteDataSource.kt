package com.example.cleancore.data.source.remote


import android.util.Log
import com.example.cleancore.data.source.remote.network.ApiResponse
import com.example.cleancore.data.source.remote.network.ApiService
import com.example.cleancore.data.source.remote.response.DataCovidResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {
    suspend fun getCovidData(): Flow<ApiResponse<DataCovidResponse>> {
        //get data from remote api
        return flow {
            try {
                val response = apiService.getCovidData()
                Log.d("dataCovid", response.confirmed.toString())
                emit(ApiResponse.Success(response))
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}
