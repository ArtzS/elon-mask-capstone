package com.example.cleancore.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cleancore.data.source.local.entity.DataCovidEntity
import com.example.cleancore.data.source.local.entity.MaskDataEntity

@Database(entities = [DataCovidEntity::class , MaskDataEntity::class], version = 3, exportSchema = false)
abstract class MaskDatabase : RoomDatabase() {

    abstract fun maskDao(): MaskDao

}