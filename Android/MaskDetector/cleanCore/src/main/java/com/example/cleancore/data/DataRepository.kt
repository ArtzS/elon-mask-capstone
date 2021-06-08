package com.example.cleancore.data

import android.util.Log
import com.example.cleancore.data.source.local.LocalDataSource
import com.example.cleancore.data.source.local.entity.DataCovidEntity
import com.example.cleancore.data.source.remote.RemoteDataSource
import com.example.cleancore.data.source.remote.network.ApiResponse
import com.example.cleancore.data.source.remote.response.DataCovidResponse
import com.example.cleancore.domain.model.DataCovid
import com.example.cleancore.domain.repository.IDataRepository
import com.example.cleancore.utils.AppExecutors
import com.example.cleancore.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IDataRepository  {

    override fun getCovidData(): Flow<Resource<DataCovidEntity>> =
        object : NetworkBoundResource<DataCovidEntity, DataCovidResponse>(){
            override fun loadFromDB(): Flow<DataCovidEntity> {
                return localDataSource.getCovidData()
            }

            override fun shouldFetch(data: DataCovidEntity?): Boolean {
                return true
            }

            override suspend fun createCall(): Flow<ApiResponse<DataCovidResponse>> {
                return remoteDataSource.getCovidData()
            }

            override suspend fun saveCallResult(data: DataCovidResponse) {
                val entity: DataCovidEntity = DataMapper.mapResponseToEntity(data)
                localDataSource.insertCovidData(entity)
            }
        }.asFlow()

}