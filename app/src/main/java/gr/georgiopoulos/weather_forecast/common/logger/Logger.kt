package gr.georgiopoulos.weather_forecast.common.logger

interface Logger {

    fun logError(tag: String, message: String)
    fun logInfo(tag: String, message: String)
}