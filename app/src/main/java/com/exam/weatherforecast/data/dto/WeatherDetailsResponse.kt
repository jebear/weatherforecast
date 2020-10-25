package com.exam.weatherforecast.data.dto

import com.exam.weatherforecast.data.model.WeatherDetailsModel
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class WeatherDetailsResponse (
    val weather: List<WeatherResponse>,
    val main: MainResponse,
    val id: String,
    val name: String
)

fun WeatherDetailsResponse.transform(isFavorite: Boolean): WeatherDetailsModel {
    return WeatherDetailsModel(
        weather = this.weather.map { it.transform() },
        main = this.main.transform(),
        id = this.id,
        name = this.name,
        isFavorite = isFavorite
    )
}
