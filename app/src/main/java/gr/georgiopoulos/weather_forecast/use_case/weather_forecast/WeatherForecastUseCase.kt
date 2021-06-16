package gr.georgiopoulos.weather_forecast.use_case.weather_forecast

import gr.georgiopoulos.weather_forecast.model.ui.weather_forecast.WeatherForecastUiModel
import gr.georgiopoulos.weather_forecast.use_case.outcome.Outcome

interface WeatherForecastUseCase {
    suspend fun getWeather(city: String): Outcome<ArrayList<WeatherForecastUiModel>>
}