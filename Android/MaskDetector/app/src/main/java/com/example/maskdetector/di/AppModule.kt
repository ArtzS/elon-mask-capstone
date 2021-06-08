package com.example.maskdetector.di

import com.example.cleancore.domain.usecase.DataUseCase
import com.example.cleancore.domain.usecase.MaskDataInteractor
import com.example.maskdetector.mainActivity.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val useCaseModule = module {
    factory<DataUseCase> { MaskDataInteractor(get()) }
}

@ExperimentalCoroutinesApi
@FlowPreview
val viewModelModule = module {
    viewModel { MainViewModel(get()) }
}