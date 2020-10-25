package com.exam.weatherforecast.base

import java.io.IOException
import java.lang.RuntimeException

class BaseErrorHandler : ErrorHandler {

    private val _networkErrorLiveEvent = SingleLiveEvent<Unit>()
    override val networkErrorLiveEvent = _networkErrorLiveEvent
    private val _ioErrorLiveEvent = SingleLiveEvent<Unit>()
    override val ioErrorLiveEvent = _ioErrorLiveEvent

    override fun handleError(error: Throwable) {
        when (error) {
            is RuntimeException -> {
                _networkErrorLiveEvent.value = Unit
            }
            is IOException -> {
                _ioErrorLiveEvent.value = Unit
            }
        }
    }
}
