package gr.georgiopoulos.weather_forecast.use_case.places

import gr.georgiopoulos.weather_forecast.use_case.places.result.PlacesResult

interface PlacesUseCase {
    fun getPredictions(query: String, placesResult: PlacesResult)
}