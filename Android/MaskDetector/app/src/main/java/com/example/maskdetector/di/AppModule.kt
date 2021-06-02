package com.example.maskdetector.di

import com.example.cleanarchitecture.core.domain.usecase.AppUseCase
import com.example.cleanarchitecture.core.domain.usecase.MaskAppInteractor
import com.example.maskdetector.mainActivity.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val useCaseModule = module {
    factory<AppUseCase> { MaskAppInteractor(get()) }
}

@ExperimentalCoroutinesApi
@FlowPreview
val viewModelModule = module {
    viewModel { MainViewModel(get()) }
}