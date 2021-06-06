package gr.georgiopoulos.weather_forecast.use_case.database_access

import gr.georgiopoulos.weather_forecast.use_case.database_access.result.DatabaseAccessResult
import gr.georgiopoulos.weather_forecast.use_case.database_access.result.load.DatabaseAccessLoadCitiesResult

interface DatabaseAccessUseCase {
    fun addCity(city: String?, databaseAccessResult: DatabaseAccessResult)
    fun loadCities(loadCitiesResult: DatabaseAccessLoadCitiesResult)
    fun deleteCity(city: String?, databaseAccessResult: DatabaseAccessResult)
}