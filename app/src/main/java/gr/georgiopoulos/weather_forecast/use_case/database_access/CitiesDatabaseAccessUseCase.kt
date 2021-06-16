package gr.georgiopoulos.weather_forecast.use_case.database_access

import gr.georgiopoulos.weather_forecast.common.logger.Logger
import gr.georgiopoulos.weather_forecast.database.CityDatabase
import gr.georgiopoulos.weather_forecast.database.dao.CityTable
import gr.georgiopoulos.weather_forecast.use_case.outcome.Empty
import gr.georgiopoulos.weather_forecast.use_case.outcome.Outcome

class CitiesDatabaseAccessUseCase(
    private val citiesDatabase: CityDatabase,
    private val logger: Logger
) : DatabaseAccessUseCase {

    override fun addCity(city: String?): Outcome<Empty> {
        if (city.isNullOrEmpty()) {
            logger.logError("CitiesDatabaseAccessUseCase -> addCity", "invalid city name $city")
            return Outcome.Error
        }
        return try {
            val cityNameTransformed = city.substringBefore(DELIMITER)
            citiesDatabase.cityTableDao().insertCity(CityTable(cityNameTransformed))
            logger.logInfo("CitiesDatabaseAccessUseCase -> addCity", "success insertion")
            Outcome.Success(Empty())
        } catch (e: Exception) {
            logger.logError(
                "CitiesDatabaseAccessUseCase -> addCity",
                "exception ${e.localizedMessage}"
            )
            Outcome.Error
        }
    }

    override fun loadCities(): Outcome<List<String>> {
        return try {
            val citiesFromDataBase = citiesDatabase.cityTableDao().loadAll()
            val result = ArrayList<String>()
            citiesFromDataBase.map { result.add(it.name) }
            Outcome.Success(result)
        } catch (e: Exception) {
            logger.logError(
                "CitiesDatabaseAccessUseCase -> loadCities",
                "exception ${e.localizedMessage}"
            )
            Outcome.Error
        }
    }

    override fun deleteCity(city: String?): Outcome<Empty> {
        if (city.isNullOrEmpty()) {
            logger.logError("CitiesDatabaseAccessUseCase -> deleteCity", "invalid city name $city")
            return Outcome.Error
        }
        return try {
            citiesDatabase.cityTableDao().deleteCity(city)
            logger.logInfo("CitiesDatabaseAccessUseCase -> deleteCity", "success deletion")
            Outcome.Success(Empty())
        } catch (e: Exception) {
            logger.logError(
                "CitiesDatabaseAccessUseCase -> deleteCity",
                "exception ${e.localizedMessage}"
            )
            Outcome.Error
        }
    }

    companion object {
        const val DELIMITER = ","
    }
}