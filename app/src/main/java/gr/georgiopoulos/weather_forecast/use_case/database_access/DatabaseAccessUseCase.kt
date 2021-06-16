package gr.georgiopoulos.weather_forecast.use_case.database_access

import gr.georgiopoulos.weather_forecast.use_case.outcome.Empty
import gr.georgiopoulos.weather_forecast.use_case.outcome.Outcome

interface DatabaseAccessUseCase {
    fun addCity(city: String?): Outcome<Empty>
    fun loadCities(): Outcome<List<String>>
    fun deleteCity(city: String?): Outcome<Empty>
}