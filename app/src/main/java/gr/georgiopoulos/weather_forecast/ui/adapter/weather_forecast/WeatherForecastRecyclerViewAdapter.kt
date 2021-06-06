package gr.georgiopoulos.weather_forecast.ui.adapter.weather_forecast

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import gr.georgiopoulos.weather_forecast.R
import gr.georgiopoulos.weather_forecast.databinding.LayoutDateBinding
import gr.georgiopoulos.weather_forecast.databinding.LayoutForecastBinding
import gr.georgiopoulos.weather_forecast.model.ui.weather_forecast.WeatherForecastUiModel
import gr.georgiopoulos.weather_forecast.ui.adapter.weather_forecast.WeatherForecastRecyclerViewAdapter.WeatherForecastType.Forecast
import gr.georgiopoulos.weather_forecast.ui.adapter.weather_forecast.WeatherForecastRecyclerViewAdapter.WeatherForecastType.Section

class WeatherForecastRecyclerViewAdapter(
    private val weatherForecast: List<WeatherForecastUiModel>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            Section.value -> {
                val binding = LayoutDateBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                DateViewHolder(binding)
            }
            else -> {
                val binding = LayoutForecastBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ForecastViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val model = weatherForecast[position]) {
            is WeatherForecastUiModel.Section -> (holder as DateViewHolder).bind(model)
            is WeatherForecastUiModel.Forecast -> (holder as ForecastViewHolder).bind(model)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (weatherForecast[position]) {
            is WeatherForecastUiModel.Section -> Section.value
            is WeatherForecastUiModel.Forecast -> Forecast.value
        }
    }

    override fun getItemCount(): Int = weatherForecast.size

    class DateViewHolder(
        private val binding: LayoutDateBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(section: WeatherForecastUiModel.Section) {
            binding.date.text = section.date
        }
    }

    class ForecastViewHolder(
        private val binding: LayoutForecastBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(forecast: WeatherForecastUiModel.Forecast) {
            with(binding) {
                hour.text = forecast.hour
                val celsius = binding.root.context.resources.getString(R.string.celsius_symbol)
                temp.text = forecast.temp + celsius
                description.text = forecast.description
                Picasso.get().load(forecast.imageUrl)
                    .placeholder(android.R.color.transparent)
                    .error(android.R.color.transparent)
                    .into(image)
            }
        }
    }

    enum class WeatherForecastType(val value: Int) {
        Section(0),
        Forecast(1)
    }
}