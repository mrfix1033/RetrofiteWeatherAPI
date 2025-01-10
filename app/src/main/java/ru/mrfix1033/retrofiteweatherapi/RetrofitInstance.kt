package ru.mrfix1033.retrofiteweatherapi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.mrfix1033.retrofiteweatherapi.utils.Util

object RetrofitInstance {
    val api: APIInterface by lazy {
        Retrofit.Builder()
            .baseUrl(Util.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIInterface::class.java)
    }
}