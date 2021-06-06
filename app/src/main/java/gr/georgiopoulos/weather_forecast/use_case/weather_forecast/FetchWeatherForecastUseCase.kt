package gr.georgiopoulos.weather_forecast.use_case.weather_forecast

import gr.georgiopoulos.weather_forecast.common.logger.CommonLogger
import gr.georgiopoulos.weather_forecast.model.transformer.weather_forecast.WeatherForecastTransformerFactory
import gr.georgiopoulos.weather_forecast.model.ui.weather_forecast.WeatherForecastUiModel
import gr.georgiopoulos.weather_forecast.model.validator.wather_forecast.Validator
import gr.georgiopoulos.weather_forecast.model.validator.wather_forecast.Validity
import gr.georgiopoulos.weather_forecast.network.client.WeatherForecastClient
import gr.georgiopoulos.weather_forecast.use_case.weather_forecast.result.WeatherResult

class FetchWeatherForecastUseCase(
    private val weatherForecastClient: WeatherForecastClient,
    private val factory: WeatherForecastTransformerFactory,
    private val validator: Validator,
    private val commonLogger: CommonLogger
) : WeatherForecastUseCase {

    override suspend fun getWeather(city: String, result: WeatherResult) {
        try {
            val response = weatherForecastClient.getWeatherForecast(city)
            val weatherList = response.data?.weatherList

            if (weatherList == null) {
                result.onError()
                return
            }

            val weatherForecast: ArrayList<WeatherForecastUiModel> = arrayListOf()
            weatherList.map { weather ->
                weather?.let {
                    val section = factory.create(it).transform()
                    if (validator.validate(section) == Validity.ValidWeatherModel) {
                        weatherForecast.add(section)

                        weather.hours?.map { hourly ->
                            if (hourly != null) {
                                val forecast = factory.create(hourly).transform()
                                if (validator.validate(forecast) == Validity.ValidWeatherModel) {
                                    weatherForecast.add(forecast)
                                }
                            }
                        }
                    }
                }
            }

            if (weatherForecast.isEmpty()) {
                result.onError()
                return
            }
            result.onSuccess(weatherForecast)

        } catch (e: Exception) {
            result.onError()
            commonLogger.logError("FetchWeatherForecastUseCase -> getWeather", e.message ?: "")
        }
    }
}