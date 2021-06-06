package gr.georgiopoulos.weather_forecast.use_case.places

import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse
import com.google.android.libraries.places.api.net.PlacesClient
import gr.georgiopoulos.weather_forecast.common.logger.Logger
import gr.georgiopoulos.weather_forecast.use_case.places.result.PlacesResult

class PlacesPredictionsUseCase(
    private val placesClient: PlacesClient,
    private val logger: Logger
) : PlacesUseCase {

    override fun getPredictions(query: String, placesResult: PlacesResult) {
        val request = FindAutocompletePredictionsRequest
            .builder()
            .setTypeFilter(TypeFilter.CITIES)
            .setQuery(query)
            .build()

        placesClient.findAutocompletePredictions(request)
            .addOnSuccessListener { response: FindAutocompletePredictionsResponse ->
                val predictions = response.autocompletePredictions
                val cities = ArrayList<String>()
                predictions.forEach { prediction ->
                    val place = prediction.getFullText(null)
                    cities.add(place.toString())
                    logger.logInfo("AddCityInteractorImpl", "City $place")
                }
                placesResult.onSuccess(cities)
            }.addOnFailureListener { exception: Exception? ->
                if (exception is ApiException) {
                    placesResult.onError()
                    logger.logError(
                        "AddCityInteractorImpl",
                        "Place not found: " + exception.statusCode
                    )
                }
            }
    }
}