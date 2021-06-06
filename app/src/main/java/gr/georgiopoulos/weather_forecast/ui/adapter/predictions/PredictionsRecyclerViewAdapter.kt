package gr.georgiopoulos.weather_forecast.ui.adapter.add_city

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gr.georgiopoulos.weather_forecast.databinding.LayoutCityBinding

class AddCityRecyclerViewAdapter(
    private val onCityClick: (String) -> Unit,
) : RecyclerView.Adapter<AddCityRecyclerViewAdapter.CityViewHolder>() {

    private var cities: MutableList<String> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val binding = LayoutCityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CityViewHolder(binding, onCityClick)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val city = cities[position]
        holder.bind(city)
    }

    override fun getItemCount(): Int {
        return cities.size
    }

    fun setCities(cities: MutableList<String>) {
        this.cities.clear()
        this.cities.addAll(cities)
        notifyDataSetChanged()
    }

    class CityViewHolder(
        private val binding: LayoutCityBinding,
        private val onCityClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(city: String) {
            with(binding) {
                cityName.text = city
                root.setOnClickListener { onCityClick(city) }
            }
        }
    }
}