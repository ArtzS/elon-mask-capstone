package com.example.cleancore.utils

import com.example.cleancore.data.source.local.entity.DataCovidEntity
import com.example.cleancore.data.source.remote.response.DataCovidResponse
import com.example.cleancore.domain.model.DataCovid

object DataMapper {
//    fun mapEntityToDomainDataCovid(input: DataCovidEntity): DataCovid = DataCovid(
//        confirmed = input.confirmed,
//        deaths = input.death,
//        recovered = input.recovered,
//        lastUpdate = input.lastUpdate
//    )
//
    fun mapResponseToEntity(input: DataCovidResponse) = DataCovidEntity(
        1,
        confirmed = input.confirmed.value,
        death = input.deaths.value,
        recovered = input.recovered.value,
        lastUpdate = input.lastUpdate
    )
}