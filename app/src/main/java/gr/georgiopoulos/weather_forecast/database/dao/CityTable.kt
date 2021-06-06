package gr.georgiopoulos.weather_forecast.database.dao

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CityTable")
class CityTable(
    @PrimaryKey
    var name: String
)