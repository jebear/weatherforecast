package com.exam.weatherforecast

import android.app.Application
import com.exam.weatherforecast.di.weatherModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class WeatherApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext( this@WeatherApp)
            modules(listOf(weatherModule))
        }
    }
}
