package com.exam.weatherforecast.data.dto

import com.exam.weatherforecast.data.model.MainModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MainResponse (
    val temp: Double,
    @Json(name = "feels_like") val feelsLike: Double,
    @Json(name = "temp_min") val tempMin: Double,
    @Json(name = "temp_max") val tempMax: Double,
    val pressure: Int,
    val humidity: Int
)

fun MainResponse.transform(): MainModel {
    return MainModel(
        temp = this.temp,
        feels_like = this.feelsLike,
        temp_min = this.tempMin,
        temp_max = this.tempMax,
        pressure = this.pressure,
        humidity = this.humidity
    )
}
