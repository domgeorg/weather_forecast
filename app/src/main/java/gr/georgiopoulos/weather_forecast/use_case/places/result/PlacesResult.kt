package gr.georgiopoulos.weather_forecast.use_case.places.result

interface PlacesResult {
    fun onSuccess(predictions: MutableList<String>)
    fun onError()
}