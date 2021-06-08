package com.example.cleancore.domain.usecase

import com.example.cleancore.domain.repository.IDataRepository


class MaskDataInteractor(private val dataRepository: IDataRepository) : DataUseCase {
    override fun getCovidData() = dataRepository.getCovidData()
}