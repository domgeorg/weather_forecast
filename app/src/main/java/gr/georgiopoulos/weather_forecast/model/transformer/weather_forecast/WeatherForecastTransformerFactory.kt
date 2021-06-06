package gr.georgiopoulos.weather_forecast.model.transformer.weather_forecast

import gr.georgiopoulos.weather_forecast.model.parser.weather_forecast.Forecast
import gr.georgiopoulos.weather_forecast.model.parser.weather_forecast.Hourly
import gr.georgiopoulos.weather_forecast.model.parser.weather_forecast.Weather

class WeatherForecastTransformerFactory {

    fun create(forecast: Forecast): Transformer {
        return when (forecast) {
            is Weather -> SectionTransformer(forecast)
            is Hourly -> ForecastTransformer(forecast)
            else -> throw IllegalArgumentException("WeatherForecastTransformerFactory creates transformers only for Forecast")
        }
    }
}