package com.example.maskdetector.mainActivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.cleancore.domain.usecase.DataUseCase

class MainViewModel(private val dataUseCase : DataUseCase) : ViewModel() {
    val dataCovid = dataUseCase.getCovidData().asLiveData()

}
