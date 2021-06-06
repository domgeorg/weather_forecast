package gr.georgiopoulos.weather_forecast.use_case.weather_forecast

import gr.georgiopoulos.weather_forecast.use_case.weather_forecast.result.WeatherResult

interface WeatherForecastUseCase {
    suspend fun getWeather(city: String, result: WeatherResult)
}