package com.example.cleancore.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import com.example.cleancore.data.source.local.entity.DataCovidEntity
import com.example.cleancore.data.source.local.entity.MaskDataEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MaskDao {

    @Query("SELECT * FROM maskdataentities ORDER BY maskdataId")
    fun getMaskData(): Flow<MaskDataEntity>

    @Insert(onConflict = IGNORE)
    fun insertPerson(maskData: MaskDataEntity)

    @Query("SELECT * FROM datacovidentities WHERE dataCovidId = 1")
    fun getCovidData():Flow<DataCovidEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCovidData(dataCovidEntity: DataCovidEntity)

}