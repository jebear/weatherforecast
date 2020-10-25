package com.exam.weatherforecast.data.dto

import com.exam.weatherforecast.data.model.WeatherDetailsModel
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherListResponse(
    val cnt: Int,
    val list: List<WeatherDetailsResponse>
)

fun WeatherListResponse.transform(id: String, isFavorite: Boolean): List<WeatherDetailsModel> {
    return this.list.map {
        it.transform(isFavorite)
    }
}
