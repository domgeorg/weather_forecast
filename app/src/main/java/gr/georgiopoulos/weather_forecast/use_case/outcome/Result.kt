package gr.georgiopoulos.weather_forecast.use_case.outcome

sealed class Outcome<out T : Any> {
    data class Success<out T : Any>(val value: T) : Outcome<T>()
    object Error : Outcome<Nothing>()
}

val <T> T.exhaustive: T
    get() = this

class Empty
