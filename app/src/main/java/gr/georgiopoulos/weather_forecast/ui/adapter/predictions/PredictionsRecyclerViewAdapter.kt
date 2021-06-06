package gr.georgiopoulos.weather_forecast.ui.adapter.predictions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gr.georgiopoulos.weather_forecast.common.extensions.autoNotify
import gr.georgiopoulos.weather_forecast.databinding.LayoutPredictionBinding
import kotlin.properties.Delegates

class PredictionsRecyclerViewAdapter(
    private val onCityClick: (String) -> Unit,
) : RecyclerView.Adapter<PredictionsRecyclerViewAdapter.PredictionViewHolder>() {

    var predictions: List<String> by Delegates.observable(emptyList()) { _, oldList, newList ->
        autoNotify(oldList, newList) { o, n -> o == n }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PredictionViewHolder {
        val binding =
            LayoutPredictionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PredictionViewHolder(binding, onCityClick)
    }

    override fun onBindViewHolder(holder: PredictionViewHolder, position: Int) {
        val city = predictions[position]
        holder.bind(city)
    }

    override fun getItemCount(): Int = predictions.size

    class PredictionViewHolder(
        private val binding: LayoutPredictionBinding,
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