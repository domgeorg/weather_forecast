package gr.georgiopoulos.weather_forecast.model.parser.weather_forecast

import com.google.gson.annotations.SerializedName

data class WeatherForecast(
    @SerializedName("data")
    val data: Data? = null
)

data class Data(
    @SerializedName("weather")
    val weatherList: List<Weather?>? = arrayListOf()
)

interface Forecast

data class Weather(
    @SerializedName("date")
    val date: String? = "",
    @SerializedName("hourly")
    val hours: List<Hourly?>? = arrayListOf()
) : Forecast

data class Hourly(
    @SerializedName("weatherIconUrl")
    val weatherIconUrl: List<WeatherIconUrl?>? = arrayListOf(),
    @SerializedName("weatherDesc")
    val weatherDesc: List<WeatherDescription?>? = arrayListOf(),
    @SerializedName("time")
    val time: String? = "",
    @SerializedName("tempC")
    val tempC: String? = ""
) : Forecast

data class WeatherDescription(
    @SerializedName("value")
    val value: String? = ""
)


data class WeatherIconUrl(
    @SerializedName("value")
    val value: String? = ""
)
