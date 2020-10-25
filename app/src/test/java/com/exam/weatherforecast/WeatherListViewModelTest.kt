package com.exam.weatherforecast

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.exam.weatherforecast.data.dto.WeatherDetailsResponse
import com.exam.weatherforecast.data.model.WeatherDetailsModel
import com.exam.weatherforecast.data.repository.WeatherRepository
import com.exam.weatherforecast.ui.WeatherListViewModel
import com.jraska.livedata.test
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response


@ExperimentalCoroutinesApi
class WeatherListViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val mockWeatherRepository = mockk<WeatherRepository>(relaxed = true)

    private val underTest = WeatherListViewModel(mockWeatherRepository)

    @Before
    fun setup() {
        Dispatchers.setMain(TestCoroutineDispatcher())
        MockKAnnotations.init(this)
    }

    @Test
    fun `on get weather list`() {
        val expectedResult = mockk<List<WeatherDetailsModel>>()

        coEvery { mockWeatherRepository.getWeatherList(CONST_MOCKK_IDS) } returns expectedResult

        underTest.getWeatherList()

        coVerify { mockWeatherRepository.getWeatherList(CONST_MOCKK_IDS) }
//
        underTest.weatherListLiveData.test()
    }

    @Test
    fun `on get weather list throws exception`() {
        val expectedError = mockk<HttpException>()

        coEvery { mockWeatherRepository.getWeatherList(CONST_MOCKK_IDS) } throws  expectedError

        underTest.weatherListLiveData.test().assertNoValue().assertHistorySize(0)
    }

    companion object {
        private const val CONST_MOCKK_IDS = "1701668,3067696,1835848"
    }
}
