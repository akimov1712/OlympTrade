package ru.topbun.olymptrade.data

import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("results") val result: List<Bar>
)
