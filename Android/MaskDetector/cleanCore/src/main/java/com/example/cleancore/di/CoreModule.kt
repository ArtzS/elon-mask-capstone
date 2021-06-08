package com.example.cleancore.di

import androidx.room.Room
import com.example.cleancore.data.DataRepository
import com.example.cleancore.data.source.local.LocalDataSource
import com.example.cleancore.data.source.local.room.MaskDatabase
import com.example.cleancore.data.source.remote.RemoteDataSource
import com.example.cleancore.data.source.remote.network.ApiService
import com.example.cleancore.domain.repository.IDataRepository
import com.example.cleancore.utils.AppExecutors
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<MaskDatabase>().maskDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            MaskDatabase::class.java, "maskDatabase.db"
        ).fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://covid19.mathdro.id/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<IDataRepository> {
        DataRepository(
            get(),
            get(),
            get()
        )
    }
}