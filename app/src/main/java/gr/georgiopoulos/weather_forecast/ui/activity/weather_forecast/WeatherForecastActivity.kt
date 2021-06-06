package gr.georgiopoulos.weather_forecast.ui.activity.weather_forecast

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import gr.georgiopoulos.weather_forecast.R
import gr.georgiopoulos.weather_forecast.databinding.ActivityWeatherForecastBinding
import gr.georgiopoulos.weather_forecast.model.ui.weather_forecast.WeatherForecastUiModel
import gr.georgiopoulos.weather_forecast.ui.adapter.weather_forecast.WeatherForecastRecyclerViewAdapter

class WeatherForecastActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWeatherForecastBinding

    private val weatherForecast by lazy {
        val uiModels: List<WeatherForecastUiModel> =
            intent?.getParcelableArrayListExtra(WEATHER_FORECAST) ?: emptyList()
        uiModels
    }

    private val city by lazy {
        intent?.getStringExtra(CITY_NAME) ?: ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherForecastBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLayout()
    }

    override fun onBackPressed() {
        overridePendingTransition(R.anim.no_change, R.anim.slide_down)
        super.onBackPressed()
    }

    private fun initLayout() {
        with(binding) {
            back.setOnClickListener {
                onBackPressed()
            }

            cityName.text = city

            val adapter = WeatherForecastRecyclerViewAdapter(weatherForecast)
            recyclerview.adapter = adapter
        }
    }

    companion object {
        const val WEATHER_FORECAST = "WEATHER_FORECAST"
        const val CITY_NAME = "CITY_NAME"
    }

}
