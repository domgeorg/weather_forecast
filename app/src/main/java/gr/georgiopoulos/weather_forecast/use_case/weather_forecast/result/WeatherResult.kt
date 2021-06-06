package gr.georgiopoulos.weather_forecast.use_case.weather_forecast.result

import gr.georgiopoulos.weather_forecast.model.ui.weather_forecast.WeatherForecastUiModel

interface WeatherResult {
    fun onSuccess(weatherForecast: ArrayList<WeatherForecastUiModel>)
    fun onError()
}