package com.longjam.sumanta.abridge.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AbridgeApi {
    val apiService: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
        return@lazy retrofit.create(ApiService::class.java)
    }
}