package com.exam.weatherforecast.base

import androidx.lifecycle.LiveData

interface ErrorHandler {
    val networkErrorLiveEvent: LiveData<Unit>
    val ioErrorLiveEvent: LiveData<Unit>

    fun handleError(error: Throwable)
}
