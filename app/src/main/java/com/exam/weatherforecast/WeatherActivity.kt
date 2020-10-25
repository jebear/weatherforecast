package com.exam.weatherforecast

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController

class WeatherActivity : AppCompatActivity() {

    private val navController by lazy {
        findNavController(R.id.weatherNavHostFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController.apply {
            graph = navInflater.inflate(R.navigation.nav_weather)
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.weatherListFragment ->
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                else ->
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
                || super.onSupportNavigateUp()
    }
}
