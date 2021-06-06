package gr.georgiopoulos.weather_forecast.model.ui.weather_forecast

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class WeatherForecastUiModel : Parcelable {
    @Parcelize
    data class Section(val date: String) : WeatherForecastUiModel()

    @Parcelize
    data class Forecast(
        val hour: String,
        val temp: String,
        val description: String,
        val imageUrl: String
    ) : WeatherForecastUiModel()
}