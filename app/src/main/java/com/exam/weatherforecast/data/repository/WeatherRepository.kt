package com.exam.weatherforecast.data.repository

import com.exam.weatherforecast.data.model.WeatherDetailsModel

interface WeatherRepository {
    suspend fun getWeatherList(
        id: String
    ): List<WeatherDetailsModel>

    suspend fun getCurrentWeather(
        city: String
    ): WeatherDetailsModel

    fun toggleFavorite(id: String, isFavorite: Boolean)

    fun isFavorite(id: String): Boolean
}
