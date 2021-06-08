package com.example.cleancore.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "maskdataentities")
data class MaskDataEntity(

    @PrimaryKey (autoGenerate = true)
    @ColumnInfo(name = "maskdataId")
    var id: Int?,

    @ColumnInfo(name = "date")
    var date: String,

    @ColumnInfo(name = "withMask")
    var withMask: String,

    @ColumnInfo(name = "withoutMask")
    var withoutMask: String
)