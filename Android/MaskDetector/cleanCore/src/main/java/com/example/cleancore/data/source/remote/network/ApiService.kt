package com.example.cleancore.data.source.remote.network

import com.example.cleancore.data.source.remote.response.DataCovidResponse
import retrofit2.http.GET

interface ApiService {
    @GET("api/countries/Indonesia")
    suspend fun getCovidData(): DataCovidResponse
}