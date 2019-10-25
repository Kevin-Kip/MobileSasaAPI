package com.revosleap.mobilesasa.requests

import com.revosleap.mobilesasa.models.MSResponse
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Path

interface APIService {
    @POST("senderID={SENDER_ID}&phone={recipient}&message={message}&api_key={API_KEY}&username={userName}")
    fun sendSMS(
        @Path("SENDER_ID") SENDER_ID: String,
        @Path("recipient") recipient: String,
        @Path("message") message: String,
        @Path("API_KEY") API_KEY: String,
        @Path("userName") userName: String
    ): Call<MSResponse>
}