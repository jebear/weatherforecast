package com.exam.weatherforecast.data.local

import android.content.Context

class WeatherLocalImpl(
    private val context: Context
) : WeatherLocal {
    private val sharedPreference =
        context.getSharedPreferences("WEATHER_PREFERENCE", Context.MODE_PRIVATE)

    override fun toggleFavorite(
        id: String,
        isFavorite: Boolean
    ) {
       var editor = sharedPreference.edit()
        editor.putBoolean(id, isFavorite)
        editor.commit()
    }

    override fun isFavorite(id: String): Boolean =
        sharedPreference.getBoolean(id, false)

}
