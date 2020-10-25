package com.exam.weatherforecast.ui

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.core.content.ContextCompat.getDrawable
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.exam.weatherforecast.R
import com.exam.weatherforecast.data.model.WeatherDetailsModel


@EpoxyModelClass(layout = R.layout.item_weather)
abstract class WeatherEpoxyModelHolder : EpoxyModelWithHolder<WeatherEpoxyModelHolder.Holder>() {
    @EpoxyAttribute
    lateinit var weatherDetail: WeatherDetailsModel

    @EpoxyAttribute
    lateinit var weatherItemClick: (WeatherDetailsModel) -> Unit

    override fun bind(holder: Holder) {
        super.bind(holder)
        with(weatherDetail) {
            holder.temp.text = main.temp.toString() + CONST_CELCIUS
            holder.city.text = name
            holder.weather.text = weather.first().main
            holder.weatherCardView.setOnClickListener {
                weatherItemClick(this)
            }
            holder.weatherCardView.setCardBackgroundColor(
                Color.parseColor(
                    when {
                        main.temp < 15 -> "#1976D2"
                        main.temp in 0.0..15.0 -> "#26C6DA"
                        main.temp in 15.0..30.0 -> "#66BB6A"
                        else -> "#FF7043"
                    }
                )
            )
            holder.favorite.apply {
                when (isFavorite){
                    true -> {
                        visibility = View.VISIBLE
                        setImageDrawable(
                            getDrawable(
                                context,
                                R.drawable.ic_favorite_on
                            )
                        )
                    }
                    else -> visibility =  View.GONE
                }
            }
        }
    }

    class Holder : EpoxyHolder() {
        lateinit var temp: TextView
        lateinit var city: TextView
        lateinit var weather: TextView
        lateinit var favorite: ImageView
        lateinit var weatherCardView: CardView

        override fun bindView(itemView: View) {
            temp = itemView.findViewById(R.id.tempTextView)
            city = itemView.findViewById(R.id.cityTextView)
            weather = itemView.findViewById(R.id.weatherTextView)
            favorite = itemView.findViewById(R.id.favoriteImageView)
            weatherCardView = itemView.findViewById(R.id.weatherCardView)
        }
    }

    companion object {
        private const val CONST_CELCIUS = " °C"
    }
}
