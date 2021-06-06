package gr.georgiopoulos.weather_forecast.model.error

sealed class Error {
    object AddCityError : Error()
    object DeleteCityError : Error()
    object LoadCitiesError: Error()
    object WeatherForecastError: Error()
}
