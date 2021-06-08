package com.example.maskdetector

import android.app.Application
import com.example.cleancore.di.databaseModule
import com.example.cleancore.di.networkModule
import com.example.cleancore.di.repositoryModule
import com.example.maskdetector.di.useCaseModule
import com.example.maskdetector.di.viewModelModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

@FlowPreview
@ExperimentalCoroutinesApi
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@App)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}