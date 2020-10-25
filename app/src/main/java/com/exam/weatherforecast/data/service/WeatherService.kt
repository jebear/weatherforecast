package com.exam.weatherforecast.data.service

import com.exam.weatherforecast.data.dto.WeatherDetailsResponse
import com.exam.weatherforecast.data.dto.WeatherListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("/data/2.5/group")
    suspend fun getWeatherList(
        @Query("id") id: String
    ): WeatherListResponse

    @GET("/data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("q") city: String
    ): WeatherDetailsResponse
}

