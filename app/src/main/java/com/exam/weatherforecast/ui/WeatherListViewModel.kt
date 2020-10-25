package com.exam.weatherforecast.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exam.weatherforecast.base.BaseViewModel
import com.exam.weatherforecast.data.model.WeatherDetailsModel
import com.exam.weatherforecast.data.repository.WeatherRepository
import kotlinx.coroutines.launch

class WeatherListViewModel(
    val repository: WeatherRepository
) : BaseViewModel() {

    private var _weatherListLiveData = MutableLiveData<List<WeatherDetailsModel>>()
    val weatherListLiveData: LiveData<List<WeatherDetailsModel>> = _weatherListLiveData

    init {
        getWeatherList()
    }

    fun getWeatherList() {
        launchWithErrorHandling(handleLoading = true) {

            val list = repository.getWeatherList(
                id = CONST_GROUP_IDS
            )

            _weatherListLiveData.value = list.onEach {
                it.isFavorite = repository.isFavorite(it.id)
            }
        }
    }

    fun isFavorite() {
        _weatherListLiveData.value?.map { it.isFavorite = repository.isFavorite(it.id) }
    }

    companion object {
        private const val CONST_GROUP_IDS = "1701668,3067696,1835848"
    }
}
