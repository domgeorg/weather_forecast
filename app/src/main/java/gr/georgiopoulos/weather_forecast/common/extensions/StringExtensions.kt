package gr.georgiopoulos.weather_forecast.common.extensions

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun String?.toHour(): String {
    if (this.isNullOrEmpty()) {
        return ""
    }

    val hours = ("0" + this.take(1)).takeIf { this.length < 4 } ?: take(2)
    return "$hours:00"
}

fun String.safeUrl(): String {
    return this.replace("http", "https")
}

@SuppressLint("SimpleDateFormat")
fun String?.formatDate(): String {
    if (this.isNullOrEmpty()) {
        return ""
    }

    var simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
    val date: Date? = try {
        simpleDateFormat.parse(this)
    } catch (e: ParseException) {
        //No need to Log this exception
        null
    }
    simpleDateFormat = SimpleDateFormat("dd MMMM yyyy")
    return if (date == null) "" else simpleDateFormat.format(date)
}