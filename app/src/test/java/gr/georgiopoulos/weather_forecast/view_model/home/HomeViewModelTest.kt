package gr.georgiopoulos.weather_forecast.view_model.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.capture
import com.nhaarman.mockitokotlin2.verify
import gr.georgiopoulos.weather_forecast.model.error.Error.*
import gr.georgiopoulos.weather_forecast.model.ui.weather_forecast.WeatherForecastUiModel
import gr.georgiopoulos.weather_forecast.use_case.database_access.DatabaseAccessUseCase
import gr.georgiopoulos.weather_forecast.use_case.outcome.Empty
import gr.georgiopoulos.weather_forecast.use_case.outcome.Outcome
import gr.georgiopoulos.weather_forecast.use_case.places.PlacesUseCase
import gr.georgiopoulos.weather_forecast.use_case.places.result.PlacesResult
import gr.georgiopoulos.weather_forecast.use_case.weather_forecast.WeatherForecastUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.mockito.*
import org.mockito.Mockito.`when`
import java.util.*
import kotlin.collections.ArrayList

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var homeViewModel: HomeViewModel

    @Mock
    private lateinit var mockPlacesUseCase: PlacesUseCase

    @Mock
    private lateinit var mockDatabaseAccessUseCase: DatabaseAccessUseCase

    @Mock
    private lateinit var mockWeatherForecastUseCase: WeatherForecastUseCase

    @Captor
    private lateinit var placesResultCaptor: ArgumentCaptor<PlacesResult>

    private val dummyCity = UUID.randomUUID().toString()

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()

    private val dummyPredictions by lazy {
        ArrayList<String>().apply {
            add(UUID.randomUUID().toString())
            add(UUID.randomUUID().toString())
            add(UUID.randomUUID().toString())
            add(UUID.randomUUID().toString())
            add(UUID.randomUUID().toString())
        }
    }

    private val dummyCities by lazy {
        ArrayList<String>().apply {
            add(UUID.randomUUID().toString())
            add(UUID.randomUUID().toString())
            add(UUID.randomUUID().toString())
            add(UUID.randomUUID().toString())
            add(UUID.randomUUID().toString())
        }
    }

    private val dummyWeatherForecast by lazy {
        ArrayList<WeatherForecastUiModel>().apply {
            add(WeatherForecastUiModel.Section("2021-06-06"))
            add(
                WeatherForecastUiModel.Forecast(
                    hour = "00:00",
                    temp = "23°C",
                    description = "Partly cloudy",
                    imageUrl = "https://cdn.worldweatheronline.com/images/wsymbols01_png_64/wsymbol_0004_black_low_cloud.png"
                )
            )
            add(
                WeatherForecastUiModel.Forecast(
                    hour = "10:00",
                    temp = "24°C",
                    description = "Partly cloudy",
                    imageUrl = "https://cdn.worldweatheronline.com/images/wsymbols01_png_64/wsymbol_0004_black_low_cloud.png"
                )
            )
            add(
                WeatherForecastUiModel.Forecast(
                    hour = "02:00",
                    temp = "22°C",
                    description = "Partly cloudy",
                    imageUrl = "https://cdn.worldweatheronline.com/images/wsymbols01_png_64/wsymbol_0004_black_low_cloud.png"
                )
            )
            add(
                WeatherForecastUiModel.Forecast(
                    hour = "03:00",
                    temp = "23°C",
                    description = "Partly cloudy",
                    imageUrl = "https://cdn.worldweatheronline.com/images/wsymbols01_png_64/wsymbol_0004_black_low_cloud.png"
                )
            )
            add(
                WeatherForecastUiModel.Forecast(
                    hour = "04:00",
                    temp = "20°C",
                    description = "Partly cloudy",
                    imageUrl = "https://cdn.worldweatheronline.com/images/wsymbols01_png_64/wsymbol_0004_black_low_cloud.png"
                )
            )
        }
    }

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        homeViewModel = HomeViewModel(
            placesUseCase = mockPlacesUseCase,
            databaseAccessUseCase = mockDatabaseAccessUseCase,
            weatherForecastUseCase = mockWeatherForecastUseCase
        )

        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    //region getPredictions
    @Test
    fun `given success when request cities predictions then should post predictions`() {
        homeViewModel.getPredictions(dummyCity)

        verify(mockPlacesUseCase).getPredictions(any(), capture(placesResultCaptor))
        placesResultCaptor.value.onSuccess(dummyPredictions)

        homeViewModel.predictions.observeForever {
            Assert.assertEquals(it, dummyPredictions)
        }
    }

    @Test
    fun `given error when request cities predictions then should post empty list`() {
        homeViewModel.getPredictions(dummyCity)

        val emptyPredictions: MutableList<String> = ArrayList()
        verify(mockPlacesUseCase).getPredictions(any(), capture(placesResultCaptor))
        placesResultCaptor.value.onError()

        homeViewModel.predictions.observeForever {
            Assert.assertEquals(it, emptyPredictions)
        }
    }
    //endregion

    //region loadCities
    @Test
    fun `given success when load cities then should post cities`() {
        `when`(mockDatabaseAccessUseCase.loadCities()).then { Outcome.Success(dummyCities) }

        homeViewModel.loadCities()

        homeViewModel.cities.observeForever {
            Assert.assertEquals(it, dummyCities)
        }
    }

    @Test
    fun `given error when load cities then should post LoadCitiesError`() {
        `when`(mockDatabaseAccessUseCase.loadCities()).then { Outcome.Error }

        homeViewModel.loadCities()

        homeViewModel.error.observeForever {
            Assert.assertEquals(it, LoadCitiesError)
        }
    }
    //endregion

    //region addCity
    @Test
    fun `given success when add city and success load cities then should post cities`() {
        `when`(mockDatabaseAccessUseCase.addCity(dummyCity)).then { Outcome.Success(Empty()) }
        `when`(mockDatabaseAccessUseCase.loadCities()).then { Outcome.Success(dummyCities) }

        homeViewModel.addCity(dummyCity)

        homeViewModel.cities.observeForever {
            Assert.assertEquals(it, dummyCities)
        }
    }

    @Test
    fun `given error when add city then should post AddCityError`() {
        `when`(mockDatabaseAccessUseCase.addCity(dummyCity)).then { Outcome.Error }

        homeViewModel.addCity(dummyCity)


        homeViewModel.error.observeForever {
            Assert.assertEquals(it, AddCityError)
        }
    }
    //endregion

    //region deleteCity
    @Test
    fun `given success when delete city and success load cities then should post cities`() {
        `when`(mockDatabaseAccessUseCase.deleteCity(dummyCity)).then { Outcome.Success(Empty()) }
        `when`(mockDatabaseAccessUseCase.loadCities()).then { Outcome.Success(dummyCities) }

        homeViewModel.deleteCity(dummyCity)


        homeViewModel.cities.observeForever {
            Assert.assertEquals(it, dummyCities)
        }
    }

    @Test
    fun `given error when delete city then should post DeleteCityError`() {
        `when`(mockDatabaseAccessUseCase.addCity(dummyCity)).then { Outcome.Error }

        homeViewModel.deleteCity(dummyCity)

        homeViewModel.error.observeForever {
            Assert.assertEquals(it, DeleteCityError)
        }
    }
    //endregion

    //region getWeather
    @Test
    fun `given success when get weather then should post city and weatherForecast`() =
        runBlocking {
            `when`(mockWeatherForecastUseCase.getWeather(dummyCity)).then {
                Outcome.Success(
                    dummyWeatherForecast
                )
            }

            homeViewModel.getWeather(dummyCity)

            homeViewModel.weatherForecast.observeForever {
                Assert.assertEquals(it, Pair(dummyCity, dummyWeatherForecast))
            }
        }

    @Test
    fun `given error when get weather then should post LoadCitiesError`() =
        runBlocking {
            `when`(mockWeatherForecastUseCase.getWeather(dummyCity)).then { Outcome.Error }

            homeViewModel.getWeather(dummyCity)

            homeViewModel.error.observeForever {
                Assert.assertEquals(it, LoadCitiesError)
            }
        }
    //endregion

    /**
     * Returns Mockito.any() as nullable type to avoid java.lang.IllegalStateException when
     * null is returned.
     */
    fun <T> any(): T = Mockito.any<T>()
}