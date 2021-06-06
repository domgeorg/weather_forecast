package gr.georgiopoulos.weather_forecast.use_case.database_access.result

interface DatabaseAccessResult {
    fun onSuccess()
    fun onError()
}