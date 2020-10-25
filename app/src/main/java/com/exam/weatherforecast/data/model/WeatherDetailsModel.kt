package com.exam.weatherforecast.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class WeatherDetailsModel(
    val weather: List<WeatherModel>,
    val main: MainModel,
    val id: String,
    val name: String,
    var isFavorite: Boolean
) : Parcelable
