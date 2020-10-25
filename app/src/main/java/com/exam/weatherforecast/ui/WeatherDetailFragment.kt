package com.exam.weatherforecast.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.exam.weatherforecast.R
import com.exam.weatherforecast.databinding.FragmentWeatherDetailBinding
import org.koin.android.viewmodel.ext.android.viewModel

class WeatherDetailFragment : Fragment() {

    private val viewModel: WeatherDetailViewModel by viewModel()

    private lateinit var binding: FragmentWeatherDetailBinding

    private val arguments: WeatherDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeatherDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val city = arguments.argumentCity

        viewModel.apply {
            getCurrentWeather(city)

            currentWeatherLiveData.observe(viewLifecycleOwner, Observer { weatherDetail ->
                binding.apply {
                    getFavorite(weatherDetail.id)

                    cityTextView.text = weatherDetail.name
                    tempTextView.text =
                        String.format(
                            getString(R.string.temperature_celcius),
                            weatherDetail.main.temp
                        )
                    weatherTextView.text = weatherDetail.weather.first().main
                    minMaxTempTextView.text = String.format(
                        getString(R.string.min_max_format),
                        weatherDetail.main.temp_max.toInt(),
                        weatherDetail.main.temp_min.toInt()
                    )
                    favoriteLiveEvent.observe(viewLifecycleOwner, Observer { isFavorite ->
                        weatherDetail.isFavorite = isFavorite
                        favoriteImageView.setImageDrawable(
                            ContextCompat.getDrawable(
                                requireContext(),
                                when (isFavorite) {
                                    true -> R.drawable.ic_favorite_on
                                    else -> R.drawable.ic_favorite_off
                                }
                            )
                        )
                    })
                    favoriteImageView.setOnClickListener {
                        viewModel.toggleFavorite(
                            weatherDetail.id,
                            weatherDetail.isFavorite
                        )
                    }
                }
            })

            errorLiveEvent.observe(viewLifecycleOwner, Observer {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            })
        }
    }
}
