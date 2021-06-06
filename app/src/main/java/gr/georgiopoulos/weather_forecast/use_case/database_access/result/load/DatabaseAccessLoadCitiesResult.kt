package gr.georgiopoulos.weather_forecast.use_case.database_access.result.load

interface DatabaseAccessLoadCitiesResult {
    fun onSuccess(cities: List<String>)
    fun onError()
}