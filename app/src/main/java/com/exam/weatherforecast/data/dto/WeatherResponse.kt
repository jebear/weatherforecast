package com.exam.weatherforecast.data.dto

import com.exam.weatherforecast.data.model.WeatherModel
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherResponse (
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

fun WeatherResponse.transform(): WeatherModel {
    return WeatherModel(
        id = this.id,
        main = this.main,
        description = this.description,
        icon = this.icon
    )
}
