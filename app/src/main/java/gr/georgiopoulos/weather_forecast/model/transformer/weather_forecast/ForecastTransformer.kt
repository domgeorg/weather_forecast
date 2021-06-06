package gr.georgiopoulos.weather_forecast.model.transformer.weather_forecast

import gr.georgiopoulos.weather_forecast.common.extensions.safeUrl
import gr.georgiopoulos.weather_forecast.common.extensions.toHour
import gr.georgiopoulos.weather_forecast.model.parser.weather_forecast.Hourly
import gr.georgiopoulos.weather_forecast.model.ui.weather_forecast.WeatherForecastUiModel

class ForecastTransformer(private val hourly: Hourly) : Transformer {

    override fun transform(): WeatherForecastUiModel {
        return with(hourly) {
            WeatherForecastUiModel.Forecast(
                hour = time?.toHour() ?: "",
                temp = tempC ?: "",
                description = weatherDesc?.firstOrNull()?.value ?: "",
                imageUrl = weatherIconUrl?.firstOrNull()?.value?.safeUrl() ?: ""
            )
        }
    }
}