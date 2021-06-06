package gr.georgiopoulos.weather_forecast.model.transformer.weather_forecast

import gr.georgiopoulos.weather_forecast.model.ui.weather_forecast.WeatherForecastUiModel

interface Transformer {
    fun transform(): WeatherForecastUiModel
}