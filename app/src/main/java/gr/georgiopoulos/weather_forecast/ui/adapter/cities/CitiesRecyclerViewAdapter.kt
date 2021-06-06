package gr.georgiopoulos.weather_forecast.ui.adapter.cities

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gr.georgiopoulos.weather_forecast.common.extensions.autoNotify
import gr.georgiopoulos.weather_forecast.databinding.LayoutCityBinding
import kotlin.properties.Delegates

class CitiesRecyclerViewAdapter(
    private val onWeatherClick: (String) -> Unit,
    private val onDeleteClick: (String) -> Unit
) : RecyclerView.Adapter<CitiesRecyclerViewAdapter.CityViewHolder>() {

    var cities: List<String> by Delegates.observable(emptyList()) { _, oldList, newList ->
        autoNotify(oldList, newList) { o, n -> o == n }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val binding = LayoutCityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CityViewHolder(binding, onWeatherClick, onDeleteClick)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val city = cities[position]
        holder.bind(city)
    }

    override fun getItemCount(): Int = cities.size

    class CityViewHolder(
        private val binding: LayoutCityBinding,
        private val onWeatherClick: (String) -> Unit,
        private val onDeleteClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(city: String) {
            with(binding) {
                cityName.text = city
                weather.setOnClickListener { onWeatherClick(city) }
                delete.setOnClickListener { onDeleteClick(city) }
            }
        }
    }
}