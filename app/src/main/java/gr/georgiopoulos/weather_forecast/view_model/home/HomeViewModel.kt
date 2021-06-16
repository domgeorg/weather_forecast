package gr.georgiopoulos.weather_forecast.view_model.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import gr.georgiopoulos.weather_forecast.model.error.Error
import gr.georgiopoulos.weather_forecast.model.ui.weather_forecast.WeatherForecastUiModel
import gr.georgiopoulos.weather_forecast.use_case.database_access.DatabaseAccessUseCase
import gr.georgiopoulos.weather_forecast.use_case.outcome.Outcome
import gr.georgiopoulos.weather_forecast.use_case.outcome.exhaustive
import gr.georgiopoulos.weather_forecast.use_case.places.PlacesUseCase
import gr.georgiopoulos.weather_forecast.use_case.places.result.PlacesResult
import gr.georgiopoulos.weather_forecast.use_case.weather_forecast.WeatherForecastUseCase
import gr.georgiopoulos.weather_forecast.view_model.base.BaseViewModel
import kotlinx.coroutines.launch

class HomeViewModel(
    private val placesUseCase: PlacesUseCase,
    private val databaseAccessUseCase: DatabaseAccessUseCase,
    private val weatherForecastUseCase: WeatherForecastUseCase
) : BaseViewModel() {

    private var predictionsMutableStream: MutableLiveData<MutableList<String>> = MutableLiveData()

    private var citiesMutableStream: MutableLiveData<List<String>> = MutableLiveData()

    private var weatherForecastMutableStream: MutableLiveData<Pair<String, ArrayList<WeatherForecastUiModel>>> =
        MutableLiveData()

    private var errorMutableStream = MutableLiveData<Error>()

    val predictions: LiveData<MutableList<String>>
        get() = predictionsMutableStream

    val cities: LiveData<List<String>>
        get() = citiesMutableStream

    val weatherForecast: LiveData<Pair<String, ArrayList<WeatherForecastUiModel>>>
        get() = weatherForecastMutableStream

    val error: LiveData<Error>
        get() = errorMutableStream

    fun getPredictions(query: String) {
        placesUseCase.getPredictions(query,
            object : PlacesResult {
                override fun onSuccess(predictions: MutableList<String>) {
                    predictionsMutableStream.value = predictions
                }

                override fun onError() {
                    predictionsMutableStream.value = mutableListOf()
                }
            })
    }

    fun loadCities() {
        when (val outcome = databaseAccessUseCase.loadCities()) {
            is Outcome.Error -> errorMutableStream.value = Error.LoadCitiesError
            is Outcome.Success -> citiesMutableStream.value = outcome.value
        }
    }

    fun addCity(city: String) {
        when (databaseAccessUseCase.addCity(city)) {
            is Outcome.Error -> errorMutableStream.value = Error.AddCityError
            is Outcome.Success -> loadCities()
        }
    }

    fun deleteCity(city: String) {
        when (databaseAccessUseCase.deleteCity(city)) {
            is Outcome.Error -> errorMutableStream.value = Error.DeleteCityError
            is Outcome.Success -> loadCities()
        }
    }

    fun getWeather(city: String) {
        uiScope.launch {
            loadingLiveData.value = true

            when (val outcome = weatherForecastUseCase.getWeather(city)) {
                is Outcome.Error -> errorMutableStream.value = Error.LoadCitiesError
                is Outcome.Success -> weatherForecastMutableStream.value = Pair(city, outcome.value)
            }.exhaustive

            loadingLiveData.value = false
        }
    }

}