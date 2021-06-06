package gr.georgiopoulos.weather_forecast.use_case.database_access

import gr.georgiopoulos.weather_forecast.common.logger.Logger
import gr.georgiopoulos.weather_forecast.database.CityDatabase
import gr.georgiopoulos.weather_forecast.database.dao.CityTable
import gr.georgiopoulos.weather_forecast.use_case.database_access.result.DatabaseAccessResult
import gr.georgiopoulos.weather_forecast.use_case.database_access.result.load.DatabaseAccessLoadCitiesResult

class CitiesDatabaseAccessUseCase(
    private val citiesDatabase: CityDatabase,
    private val logger: Logger
) : DatabaseAccessUseCase {

    override fun addCity(city: String?, databaseAccessResult: DatabaseAccessResult) {
        if (city.isNullOrEmpty()) {
            databaseAccessResult.onError()
            logger.logError("CitiesDatabaseAccessUseCase -> addCity", "invalid city name $city")
            return
        }
        try {
            val cityNameTransformed = city.substringBefore(DELIMITER)
            citiesDatabase.cityTableDao().insertCity(CityTable(cityNameTransformed))
            databaseAccessResult.onSuccess()
            logger.logInfo("CitiesDatabaseAccessUseCase -> addCity", "success insertion")
        } catch (e: Exception) {
            databaseAccessResult.onError()
            logger.logError(
                "CitiesDatabaseAccessUseCase -> addCity",
                "exception ${e.localizedMessage}"
            )
        }
    }

    override fun loadCities(loadCitiesResult: DatabaseAccessLoadCitiesResult) {
        try {
            val citiesFromDataBase = citiesDatabase.cityTableDao().loadAll()
            val result = ArrayList<String>()
            citiesFromDataBase.map { result.add(it.name) }
            loadCitiesResult.onSuccess(result)
        } catch (e: Exception) {
            loadCitiesResult.onError()
            logger.logError(
                "CitiesDatabaseAccessUseCase -> loadCities",
                "exception ${e.localizedMessage}"
            )
        }
    }

    override fun deleteCity(city: String?, databaseAccessResult: DatabaseAccessResult) {
        if (city.isNullOrEmpty()) {
            databaseAccessResult.onError()
            logger.logError("CitiesDatabaseAccessUseCase -> deleteCity", "invalid city name $city")
            return
        }
        try {
            citiesDatabase.cityTableDao().deleteCity(city)
            databaseAccessResult.onSuccess()
            logger.logInfo("CitiesDatabaseAccessUseCase -> deleteCity", "success deletion")
        } catch (e: Exception) {
            databaseAccessResult.onError()
            logger.logError(
                "CitiesDatabaseAccessUseCase -> deleteCity",
                "exception ${e.localizedMessage}"
            )
        }
    }

    companion object{
        const val DELIMITER = ","
    }
}