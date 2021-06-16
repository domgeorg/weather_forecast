package gr.georgiopoulos.weather_forecast.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CityTableDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCity(cityTable: CityTable)

    @Query("DELETE FROM CityTable WHERE name = :name")
    fun deleteCity(name: String)

    /**
     * This is an example, never fetch everything from a table
     * */
    @Query("select * from CityTable order by name ASC")
    fun loadAll(): List<CityTable>
}