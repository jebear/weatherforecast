package com.exam.weatherforecast.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.exam.weatherforecast.databinding.FragmentWeatherListBinding
import kotlinx.android.synthetic.main.fragment_weather_list.*
import org.koin.android.viewmodel.ext.android.viewModel

class WeatherListFragment : Fragment() {

    private val viewModel by viewModel<WeatherListViewModel>()
    private lateinit var binding: FragmentWeatherListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeatherListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.apply {
//            getWeatherList()

            binding.apply {
                weatherListLiveData.observe(viewLifecycleOwner, Observer {

                    weatherEpoxyRecyclerView.withModels {
                        it.forEach { detail ->
                            weatherEpoxyModelHolder {
                                id(detail.id)
                                weatherDetail(detail)
                                weatherItemClick {
                                    findNavController().navigate(
                                        WeatherListFragmentDirections.actionWeatherListFragmentToWeatherDetailFragment(
                                            argumentCity = detail.name
                                        )
                                    )
                                }
                            }
                        }
                    }
                })

                swipeRefreshLayout.setOnRefreshListener {
                    getWeatherList()
                    swipeRefreshLayout.isRefreshing = false
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.isFavorite()
    }
}
