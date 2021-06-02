package com.example.maskdetector

import android.app.Application
import com.example.maskdetector.di.useCaseModule
import com.example.maskdetector.di.viewModelModule
import com.example.cleancore.di.NetworkModule
import com.example.cleancore.di.RepositroyModule
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
                    RepositroyModule,
                    useCaseModule,
                    viewModelModule,
                    NetworkModule
                )
            )
        }
    }
}