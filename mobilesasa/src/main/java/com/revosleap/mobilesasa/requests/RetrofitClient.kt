package com.revosleap.mobilesasa.requests

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var retrofit: Retrofit? = null
    private val gson = GsonBuilder()
        .setLenient()
        .create()

    fun getClient(baseURL: String): Retrofit? {
        return when (retrofit) {
            null -> Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
            else -> retrofit
        }
    }
}