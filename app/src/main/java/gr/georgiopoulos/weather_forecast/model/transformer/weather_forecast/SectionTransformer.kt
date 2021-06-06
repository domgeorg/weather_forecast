package gr.georgiopoulos.weather_forecast.model.transformer.weather_forecast

import gr.georgiopoulos.weather_forecast.common.extensions.formatDate
import gr.georgiopoulos.weather_forecast.model.parser.weather_forecast.Weather
import gr.georgiopoulos.weather_forecast.model.ui.weather_forecast.WeatherForecastUiModel

class SectionTransformer(private val weather: Weather) : Transformer {

    override fun transform(): WeatherForecastUiModel {
        return WeatherForecastUiModel.Section(date = weather.date?.formatDate() ?: "")
    }
}