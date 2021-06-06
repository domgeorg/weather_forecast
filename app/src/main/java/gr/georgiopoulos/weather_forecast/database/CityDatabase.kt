package gr.georgiopoulos.weather_forecast.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import gr.georgiopoulos.weather_forecast.database.dao.CityTable
import gr.georgiopoulos.weather_forecast.database.dao.CityTableDao

@Database(entities = [CityTable::class], version = 1, exportSchema = false)
abstract class CityDatabase : RoomDatabase() {

    companion object {
        private const val NAME = "CityDB"

        fun get(context: Context): CityDatabase {
            return Room.databaseBuilder(context.applicationContext, CityDatabase::class.java, NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    abstract fun cityTableDao(): CityTableDao
}