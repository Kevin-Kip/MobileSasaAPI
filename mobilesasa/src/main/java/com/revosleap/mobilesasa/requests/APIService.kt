package com.revosleap.mobilesasa.requests

import com.revosleap.mobilesasa.models.MobileSasaResponse
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

interface APIService {
    @POST("express-post?")
    fun sendSMS(
        @Query("senderID") SENDER_ID: String,
        @Query("phone") recipient: String,
        @Query("message") message: String,
        @Query("api_key") API_KEY: String,
        @Query("username") userName: String
    ): Call<MobileSasaResponse>
}