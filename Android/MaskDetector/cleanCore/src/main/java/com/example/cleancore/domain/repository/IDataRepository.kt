package com.example.cleancore.domain.repository

import com.example.cleancore.data.Resource
import com.example.cleancore.data.source.local.entity.DataCovidEntity
import com.example.cleancore.domain.model.DataCovid
import kotlinx.coroutines.flow.Flow

interface IDataRepository {
    fun getCovidData(): Flow<Resource<DataCovidEntity>>
}
