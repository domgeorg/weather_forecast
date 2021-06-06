package gr.georgiopoulos.weather_forecast.network.client

import gr.georgiopoulos.weather_forecast.common.DefinitionsApi
import gr.georgiopoulos.weather_forecast.model.parser.weather_forecast.WeatherForecast
import gr.georgiopoulos.weather_forecast.network.api.WeatherForecastApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class WeatherForecastClient {

    private var weatherForecastApi: WeatherForecastApi

    init {
        weatherForecastApi = getRetrofit().create(WeatherForecastApi::class.java)
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(DefinitionsApi.DOMAIN)
            .client(getOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getOkHttpClient() = OkHttpClient().newBuilder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .writeTimeout(60L, TimeUnit.SECONDS)
        .build()

    suspend fun getWeatherForecast(city: String): WeatherForecast {
        return weatherForecastApi.getWeatherForecast(
            key = DefinitionsApi.KEY,
            city = city,
            days = DefinitionsApi.DAYS,
            format = DefinitionsApi.FORMAT
        )
    }
}