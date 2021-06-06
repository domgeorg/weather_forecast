package gr.georgiopoulos.weather_forecast.view_model.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import gr.georgiopoulos.weather_forecast.model.error.Error
import gr.georgiopoulos.weather_forecast.model.ui.weather_forecast.WeatherForecastUiModel
import gr.georgiopoulos.weather_forecast.use_case.database_access.DatabaseAccessUseCase
import gr.georgiopoulos.weather_forecast.use_case.database_access.result.DatabaseAccessResult
import gr.georgiopoulos.weather_forecast.use_case.database_access.result.load.DatabaseAccessLoadCitiesResult
import gr.georgiopoulos.weather_forecast.use_case.places.PlacesUseCase
import gr.georgiopoulos.weather_forecast.use_case.places.result.PlacesResult
import gr.georgiopoulos.weather_forecast.use_case.weather_forecast.WeatherForecastUseCase
import gr.georgiopoulos.weather_forecast.use_case.weather_forecast.result.WeatherResult
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
        databaseAccessUseCase.loadCities(object : DatabaseAccessLoadCitiesResult {
            override fun onSuccess(cities: List<String>) {
                citiesMutableStream.value = cities
            }

            override fun onError() {
                errorMutableStream.value = Error.LoadCitiesError
            }
        })
    }

    fun addCity(city: String) {
        databaseAccessUseCase.addCity(city,
            object : DatabaseAccessResult {
                override fun onSuccess() {
                    loadCities()
                }

                override fun onError() {
                    errorMutableStream.value = Error.AddCityError
                }
            })
    }

    fun deleteCity(city: String) {
        databaseAccessUseCase.deleteCity(city,
            object : DatabaseAccessResult {
                override fun onSuccess() {
                    loadCities()
                }

                override fun onError() {
                    errorMutableStream.value = Error.DeleteCityError
                }
            })
    }

    fun getWeather(city: String) {
        uiScope.launch {
            loadingLiveData.value = true

            weatherForecastUseCase.getWeather(city,
                object : WeatherResult {
                    override fun onSuccess(weatherForecast: ArrayList<WeatherForecastUiModel>) {
                        weatherForecastMutableStream.value = Pair(city, weatherForecast)
                    }

                    override fun onError() {
                        errorMutableStream.value = Error.LoadCitiesError
                    }
                })

            loadingLiveData.value = false
        }
    }

}