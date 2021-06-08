package com.example.cleancore.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "datacovidentities")
data class DataCovidEntity(

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "dataCovidId")
    var id: Int,

    @ColumnInfo(name = "confirmed")
    var confirmed: Int,

    @ColumnInfo(name = "death")
    var death: Int,

    @ColumnInfo(name = "recovered")
    var recovered: Int,

    @ColumnInfo(name = "lastUpdate")
    var lastUpdate: String

)
