package ru.mrfix1033.retrofiteweatherapi

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.mrfix1033.retrofiteweatherapi.models.CurrentWeather

interface APIInterface {
    @GET("weather?")
    suspend fun getCurrentWeather(
        @Query("q") city : String,
        @Query("appid") api_key : String,
        @Query("units") units: String
    ) : Response<CurrentWeather>
}