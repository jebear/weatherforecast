package com.exam.weatherforecast.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

open class BaseViewModel(
    errorHandler: ErrorHandler = BaseErrorHandler()
): ViewModel(), ErrorHandler by errorHandler{

    var isLoading: Boolean
        get() = _isLoadingLiveData.value ?: false
        private set(value) {
            _isLoadingLiveData.postValue(value)
        }

    private val _isLoadingLiveData = SingleLiveEvent<Boolean>()
    val isLoadingLiveData: LiveData<Boolean> = _isLoadingLiveData

    protected fun launchWithErrorHandling(
        context: CoroutineContext = EmptyCoroutineContext,
        handleLoading: Boolean = false,
        call: suspend CoroutineScope.() -> Unit
    ): Job = viewModelScope.launch(context) {
        try {
            if (handleLoading) isLoading = true
            call()
        } catch (error: Throwable) {
            handleError(error)
        } finally {
            if (handleLoading) isLoading = false
        }
    }
}
