package com.example.cleancore.data.source.local

import com.example.cleancore.data.source.local.entity.DataCovidEntity
import com.example.cleancore.data.source.local.entity.MaskDataEntity
import com.example.cleancore.data.source.local.room.MaskDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val maskDao: MaskDao) {
    fun getMLData(): Flow<MaskDataEntity> = maskDao.getMaskData()

    fun getCovidData(): Flow<DataCovidEntity> = maskDao.getCovidData()

    suspend fun insertCovidData(covidData: DataCovidEntity) = maskDao.insertCovidData(covidData)

}