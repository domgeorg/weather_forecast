package gr.georgiopoulos.weather_forecast.model.validator.wather_forecast

import gr.georgiopoulos.weather_forecast.model.ui.weather_forecast.WeatherForecastUiModel
import gr.georgiopoulos.weather_forecast.model.ui.weather_forecast.WeatherForecastUiModel.Forecast
import gr.georgiopoulos.weather_forecast.model.ui.weather_forecast.WeatherForecastUiModel.Section

class WeatherForecastValidator : Validator {

    override fun validate(weatherForecastUiModel: WeatherForecastUiModel): Validity {
        return when (weatherForecastUiModel) {
            is Section -> {
                if (weatherForecastUiModel.date.isEmpty()) {
                    Validity.InvalidWeatherModel
                } else Validity.ValidWeatherModel
            }

            is Forecast -> {
                with(weatherForecastUiModel) {
                    if (hour.isEmpty() || temp.isEmpty() || description.isEmpty() || imageUrl.isEmpty()) {
                        Validity.InvalidWeatherModel
                    } else Validity.ValidWeatherModel
                }
            }
        }
    }
}