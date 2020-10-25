package com.exam.weatherforecast.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.exam.weatherforecast.base.BaseViewModel
import com.exam.weatherforecast.base.SingleLiveEvent
import com.exam.weatherforecast.data.model.WeatherDetailsModel
import com.exam.weatherforecast.data.repository.WeatherRepository
import retrofit2.HttpException

class WeatherDetailViewModel(
    private val repository: WeatherRepository
) : BaseViewModel() {

    private val _favoriteLiveEvent = SingleLiveEvent<Boolean>()
    val favoriteLiveEvent: LiveData<Boolean> = _favoriteLiveEvent

    private val _currentWeatherLiveData = MutableLiveData<WeatherDetailsModel>()
    val currentWeatherLiveData: LiveData<WeatherDetailsModel> = _currentWeatherLiveData

    private val _errorLiveEvent = SingleLiveEvent<String>()
    val errorLiveEvent: LiveData<String> = _errorLiveEvent

    fun getCurrentWeather(city: String){
        launchWithErrorHandling(handleLoading = true) {
            _currentWeatherLiveData.value = repository.getCurrentWeather(city)
        }
    }

    fun toggleFavorite(id: String, isFavorite: Boolean) {
        repository.toggleFavorite(id, isFavorite.not())
        getFavorite(id)
    }

    fun getFavorite(id: String) {
        _favoriteLiveEvent.value = repository.isFavorite(id)
    }

    override fun handleError(error: Throwable) {
        when (error) {
            is HttpException ->
                _errorLiveEvent.value = error.message
            else -> super.handleError(error)
        }
    }
}
