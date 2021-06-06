package gr.georgiopoulos.weather_forecast.common.logger

import android.util.Log

class CommonLogger : Logger {

    override fun logError(tag: String, message: String) {
        Log.e(tag, message)
    }

    override fun logInfo(tag: String, message: String) {
        Log.i(tag, message)
    }
}