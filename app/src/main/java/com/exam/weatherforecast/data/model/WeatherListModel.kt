package com.exam.weatherforecast.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeatherListModel(
    val cnt: Int,
    val list: List<WeatherDetailsModel>
) : Parcelable
