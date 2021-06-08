package com.example.cleancore.domain.usecase

import com.example.cleancore.data.Resource
import com.example.cleancore.data.source.local.entity.DataCovidEntity
import com.example.cleancore.domain.model.DataCovid
import kotlinx.coroutines.flow.Flow

interface DataUseCase {
    fun getCovidData(): Flow<Resource<DataCovidEntity>>
}