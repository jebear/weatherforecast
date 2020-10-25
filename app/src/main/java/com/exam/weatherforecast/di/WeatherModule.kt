package com.exam.weatherforecast.di

import android.content.Context
import android.net.ConnectivityManager
import android.os.Looper
import android.widget.Toast
import com.exam.weatherforecast.data.local.WeatherLocal
import com.exam.weatherforecast.data.local.WeatherLocalImpl
import com.exam.weatherforecast.data.repository.WeatherRepository
import com.exam.weatherforecast.data.repository.WeatherRepositoryImpl
import com.exam.weatherforecast.data.service.WeatherService
import com.exam.weatherforecast.ui.WeatherDetailViewModel
import com.exam.weatherforecast.ui.WeatherListViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


val weatherModule = module {

    viewModel {
        WeatherListViewModel(repository = get())
    }

    viewModel {
        WeatherDetailViewModel(repository = get())
    }

    // repository
    factory<WeatherRepository> { WeatherRepositoryImpl(service = get(), local = get()) }

    // local
    factory<WeatherLocal> { WeatherLocalImpl(context = androidContext()) }

    // service
    single { get<Retrofit>(named("WeatherRetrofit")).create(WeatherService::class.java) }

    single(named("WeatherRetrofit")) {
        Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org")
            .client(get(named("OkHttpClient")))
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
    }

    single {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    single(named("OkHttpClient")) {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val url: HttpUrl = chain.request()
                    .url
                    .newBuilder()
                    .addQueryParameter("appid", "a1353d7c6c3f7500528cecb49e1fac6d")
                    .addQueryParameter("units", "metric")
                    .build()

                val connectivityManager = androidContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val netInfo = connectivityManager.activeNetworkInfo
                val isConnected = netInfo != null && netInfo.isConnected
                if (!isConnected){
                    Looper.prepare()
                    android.os.Handler(Looper.getMainLooper()).post(Runnable {
                        Toast.makeText(androidContext(),"No internet connection", Toast.LENGTH_SHORT).show()
                    })
                    Looper.loop()
                }

                val response = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()
                chain.proceed(response)
            }
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

}
