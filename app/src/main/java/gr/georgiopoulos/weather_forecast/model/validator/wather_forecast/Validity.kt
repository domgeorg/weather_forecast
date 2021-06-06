package gr.georgiopoulos.weather_forecast.model.validator.wather_forecast

sealed class Validity {
    object ValidWeatherModel : Validity()
    object InvalidWeatherModel : Validity()
}
