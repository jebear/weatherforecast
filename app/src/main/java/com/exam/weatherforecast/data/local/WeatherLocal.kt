package com.exam.weatherforecast.data.local

interface WeatherLocal {
    fun toggleFavorite(id: String, isFavorite: Boolean)
    fun isFavorite(id: String) : Boolean
}
