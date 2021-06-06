package gr.georgiopoulos.weather_forecast.ui.activity.home

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.afollestad.materialdialogs.MaterialDialog
import com.google.android.libraries.places.api.Places
import com.google.android.material.bottomsheet.BottomSheetBehavior
import gr.georgiopoulos.weather_forecast.R
import gr.georgiopoulos.weather_forecast.common.definitions.PlacesApi
import gr.georgiopoulos.weather_forecast.common.extensions.afterTextChangedDebounce
import gr.georgiopoulos.weather_forecast.common.logger.CommonLogger
import gr.georgiopoulos.weather_forecast.database.CityDatabase
import gr.georgiopoulos.weather_forecast.databinding.ActivityHomeBinding
import gr.georgiopoulos.weather_forecast.model.error.Error.*
import gr.georgiopoulos.weather_forecast.model.transformer.weather_forecast.WeatherForecastTransformerFactory
import gr.georgiopoulos.weather_forecast.model.validator.wather_forecast.WeatherForecastValidator
import gr.georgiopoulos.weather_forecast.network.client.WeatherForecastClient
import gr.georgiopoulos.weather_forecast.ui.activity.base.BaseActivity
import gr.georgiopoulos.weather_forecast.ui.activity.weather_forecast.WeatherForecastActivity
import gr.georgiopoulos.weather_forecast.ui.adapter.cities.CitiesRecyclerViewAdapter
import gr.georgiopoulos.weather_forecast.ui.adapter.predictions.PredictionsRecyclerViewAdapter
import gr.georgiopoulos.weather_forecast.use_case.database_access.CitiesDatabaseAccessUseCase
import gr.georgiopoulos.weather_forecast.use_case.places.PlacesPredictionsUseCase
import gr.georgiopoulos.weather_forecast.use_case.weather_forecast.FetchWeatherForecastUseCase
import gr.georgiopoulos.weather_forecast.view_model.base.BaseViewModelFactory
import gr.georgiopoulos.weather_forecast.view_model.home.HomeViewModel
import java.util.*

class HomeActivity : BaseActivity<HomeViewModel>() {

    private lateinit var binding: ActivityHomeBinding

    private var predictionsRecyclerViewAdapter: PredictionsRecyclerViewAdapter? = null

    private var citiesRecyclerViewAdapter: CitiesRecyclerViewAdapter? = null

    private val placesClient by lazy {
        Places.initialize(this, PlacesApi.KEY)
        Places.createClient(this)
    }

    private val placesUseCase by lazy {
        PlacesPredictionsUseCase(placesClient, CommonLogger())
    }

    private val cityDataBase by lazy {
        CityDatabase.get(this)
    }

    private val dataBaseAccessUseCase by lazy {
        CitiesDatabaseAccessUseCase(cityDataBase, CommonLogger())
    }

    private val weatherForecastUseCase by lazy {
        FetchWeatherForecastUseCase(
            weatherForecastClient = WeatherForecastClient(),
            factory = WeatherForecastTransformerFactory(),
            validator = WeatherForecastValidator(),
            commonLogger = CommonLogger()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLayout()
        initViewModel()
        viewModel?.loadCities()
    }

    private fun initLayout() {
        with(binding) {
            back.setOnClickListener { showExitDialog() }

            with(bottomSheet) {
                predictionsRecyclerViewAdapter =
                    PredictionsRecyclerViewAdapter { city ->
                        cityEditText.setText(city)
                    }

                predictionsRecyclerView.adapter = predictionsRecyclerViewAdapter

                cityEditText.afterTextChangedDebounce(delayMillis) { input ->
                    viewModel?.getPredictions(input.trim())
                }

                add.setOnClickListener {
                    val city = cityEditText.text.toString()
                    viewModel?.addCity(city)
                    val bottomSheetBehavior = BottomSheetBehavior.from(addCity)
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                }
            }

            citiesRecyclerViewAdapter =
                CitiesRecyclerViewAdapter(
                    onWeatherClick = { viewModel?.getWeather(it) },
                    onDeleteClick = { viewModel?.deleteCity(it) }
                )

            citiesRecyclerview.adapter = citiesRecyclerViewAdapter
        }
    }

    private fun initViewModel() {
        val viewModelFactory = BaseViewModelFactory {
            HomeViewModel(placesUseCase, dataBaseAccessUseCase, weatherForecastUseCase)
        }

        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(HomeViewModel::class.java)

        initViewModelState(binding.loading)

        viewModel?.predictions?.observe(this, { predictions ->
            if (isFinishing) {
                return@observe
            }
            predictionsRecyclerViewAdapter?.predictions = predictions
        })

        viewModel?.cities?.observe(this, { cities ->
            if (isFinishing) {
                return@observe
            }
            citiesRecyclerViewAdapter?.cities = cities
        })

        viewModel?.weatherForecast?.observe(this, {
            if (isFinishing) {
                return@observe
            }
            val city = it.first
            val weatherForecast = it.second
            val intent = Intent(this, WeatherForecastActivity::class.java)
            intent.putExtra(WeatherForecastActivity.CITY_NAME, city)
            intent.putParcelableArrayListExtra(
                WeatherForecastActivity.WEATHER_FORECAST,
                weatherForecast
            )
            startActivity(intent)
            overridePendingTransition(R.anim.slide_up, R.anim.no_change)
        })

        viewModel?.error?.observe(this, { error ->
            if (isFinishing) {
                return@observe
            }

            val errorMessage = when (error) {
                AddCityError -> R.string.add_city_error
                DeleteCityError -> R.string.delete_city_error
                LoadCitiesError -> R.string.load_cities_error
                WeatherForecastError -> R.string.load_weather_error
            }

            MaterialDialog(this).show {
                title(R.string.app_name)
                message(errorMessage)
                positiveButton(
                    text = getString(R.string.ok),
                    click = { dismiss() })
            }
        })
    }

    private fun showExitDialog() {
        if (isFinishing) {
            return
        }
        MaterialDialog(this).show {
            title(R.string.app_name)
            message(R.string.exit_message)
            positiveButton(
                text = getString(R.string.yes),
                click = { this@HomeActivity.onBackPressed() })
            negativeButton(
                text = getString(R.string.no),
                click = { dismiss() })
        }
    }

    companion object {
        const val delayMillis = 300L
    }
}