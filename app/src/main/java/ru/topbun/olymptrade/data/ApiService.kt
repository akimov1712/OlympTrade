package ru.topbun.olymptrade.data

import retrofit2.http.GET

interface ApiService {


    @GET("aggs/ticker/AAPL/range/30/minute/2022-01-09/2023-01-09?adjusted=true&sort=asc&limit=50000&apiKey=paIA2qRaKvMoIV4J_oJJ7pw__y6s7juX")
    suspend fun loadBars(): Response

}