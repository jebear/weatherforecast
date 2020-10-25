package com.exam.weatherforecast.data.repository

import com.exam.weatherforecast.data.dto.transform
import com.exam.weatherforecast.data.local.WeatherLocal
import com.exam.weatherforecast.data.model.WeatherDetailsModel
import com.exam.weatherforecast.data.service.WeatherService

class WeatherRepositoryImpl(
    private val service: WeatherService,
    private val local: WeatherLocal
) : WeatherRepository {
    override suspend fun getWeatherList(
        id: String
    ): List<WeatherDetailsModel> =
        service.getWeatherList(id).transform(id, local.isFavorite(id))

    override suspend fun getCurrentWeather(
        id: String
    ): WeatherDetailsModel =
        service.getCurrentWeather(id).transform(local.isFavorite(id))

    override fun toggleFavorite(id: String, isFavorite: Boolean) =
        local.toggleFavorite(id, isFavorite)

    override fun isFavorite(id: String): Boolean =
        local.isFavorite(id)
}
