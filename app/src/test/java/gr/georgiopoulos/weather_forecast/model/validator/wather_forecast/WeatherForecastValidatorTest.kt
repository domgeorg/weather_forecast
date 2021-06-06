package gr.georgiopoulos.weather_forecast.model.validator.wather_forecast

import gr.georgiopoulos.weather_forecast.model.ui.weather_forecast.WeatherForecastUiModel
import org.junit.Assert.*
import org.junit.Test

class WeatherForecastValidatorTest{

    @Test
    fun`given WeatherForecastUiModel Section with empty date when validate then should return Validity InvalidWeatherModel`(){
        val weatherForecastUiModel = WeatherForecastUiModel.Section(date = "")

        val actual = WeatherForecastValidator().validate(weatherForecastUiModel)
        val expected = Validity.InvalidWeatherModel

        assertEquals(expected, actual)
    }

    @Test
    fun`given a valid WeatherForecastUiModel Section when validate then should return Validity ValidWeatherModel`(){
        val weatherForecastUiModel = WeatherForecastUiModel.Section(date = "2021-06-04")

        val actual = WeatherForecastValidator().validate(weatherForecastUiModel)
        val expected = Validity.ValidWeatherModel

        assertEquals(expected, actual)
    }

    @Test
    fun`given WeatherForecastUiModel Forecast with empty hour when validate then should return Validity InvalidWeatherModel`(){
        val weatherForecastUiModel = WeatherForecastUiModel.Forecast(
            hour = "",
            temp = "24",
            description = "Partly cloudy",
            imageUrl = "https://cdn.worldweatheronline.com/images/wsymbols01_png_64/wsymbol_0004_black_low_cloud.png"
        )

        val actual = WeatherForecastValidator().validate(weatherForecastUiModel)
        val expected = Validity.InvalidWeatherModel

        assertEquals(expected, actual)
    }

    @Test
    fun`given WeatherForecastUiModel Forecast with empty temp when validate then should return Validity InvalidWeatherModel`(){
        val weatherForecastUiModel = WeatherForecastUiModel.Forecast(
            hour = "12:00",
            temp = "",
            description = "Partly cloudy",
            imageUrl = "https://cdn.worldweatheronline.com/images/wsymbols01_png_64/wsymbol_0004_black_low_cloud.png"
        )

        val actual = WeatherForecastValidator().validate(weatherForecastUiModel)
        val expected = Validity.InvalidWeatherModel

        assertEquals(expected, actual)
    }

    @Test
    fun`given WeatherForecastUiModel Forecast with empty description when validate then should return Validity InvalidWeatherModel`(){
        val weatherForecastUiModel = WeatherForecastUiModel.Forecast(
            hour = "12:00",
            temp = "24",
            description = "",
            imageUrl = "https://cdn.worldweatheronline.com/images/wsymbols01_png_64/wsymbol_0004_black_low_cloud.png"
        )

        val actual = WeatherForecastValidator().validate(weatherForecastUiModel)
        val expected = Validity.InvalidWeatherModel

        assertEquals(expected, actual)
    }

    @Test
    fun`given WeatherForecastUiModel Forecast with empty imageUrl when validate then should return Validity InvalidWeatherModel`(){
        val weatherForecastUiModel = WeatherForecastUiModel.Forecast(
            hour = "12:00",
            temp = "24",
            description = "Partly cloudy",
            imageUrl = ""
        )

        val actual = WeatherForecastValidator().validate(weatherForecastUiModel)
        val expected = Validity.InvalidWeatherModel

        assertEquals(expected, actual)
    }

    @Test
    fun`given a valid WeatherForecastUiModel Forecast when validate then should return Validity ValidWeatherModel`(){
        val weatherForecastUiModel = WeatherForecastUiModel.Forecast(
            hour = "12:00",
            temp = "24",
            description = "Partly cloudy",
            imageUrl = "https://cdn.worldweatheronline.com/images/wsymbols01_png_64/wsymbol_0004_black_low_cloud.png"
        )

        val actual = WeatherForecastValidator().validate(weatherForecastUiModel)
        val expected = Validity.ValidWeatherModel

        assertEquals(expected, actual)
    }
}