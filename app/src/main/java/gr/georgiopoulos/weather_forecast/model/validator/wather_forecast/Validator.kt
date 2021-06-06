package gr.georgiopoulos.weather_forecast.model.validator.wather_forecast

import gr.georgiopoulos.weather_forecast.model.ui.weather_forecast.WeatherForecastUiModel

interface Validator {
    fun validate(weatherForecastUiModel: WeatherForecastUiModel): Validity
}