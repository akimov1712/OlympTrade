package ru.topbun.olymptrade.data

import android.icu.util.Calendar
import androidx.compose.runtime.Immutable
import com.google.gson.annotations.SerializedName
import java.util.Date

@Immutable
data class Bar(
    @SerializedName("o") val open: Float,
    @SerializedName("c") val close: Float,
    @SerializedName("l") val low: Float,
    @SerializedName("h") val high: Float,
    @SerializedName("t") val time: Long,
){
    val calendar: Calendar
        get() {
            return Calendar.getInstance().apply {
                time = Date(this@Bar.time)
            }
        }
}
