package gr.georgiopoulos.weather_forecast.network.api

import gr.georgiopoulos.weather_forecast.model.parser.weather_forecast.WeatherForecast
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherForecastApi {

    @GET("/premium/v1/weather.ashx")
    suspend fun getWeatherForecast(
        @Query("key") key: String,
        @Query("q", encoded = true) city: String,
        @Query("num_of_days") days: String,
        @Query("format") format: String,
        @Query("tp") tp: String
    ): WeatherForecast

}