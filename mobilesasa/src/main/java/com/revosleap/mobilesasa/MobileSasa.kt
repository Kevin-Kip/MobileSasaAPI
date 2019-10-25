package com.revosleap.mobilesasa

import com.revosleap.mobilesasa.models.MobileSasaResponse
import com.revosleap.mobilesasa.requests.APIClient
import com.revosleap.mobilesasa.requests.APIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MobileSasa {
    private var userName: String? = null
    private var API_KEY: String? = null
    private var token: String? = null
    private var SENDER_ID: String? = "MOBILESASA"
    private var apiService: APIService? = null

    constructor(userName: String, API_KEY: String) {
        this.userName = userName
        this.API_KEY = API_KEY
        apiService = APIClient.getAPIService()
    }

//    constructor(userName: String, API_KEY: String, token: String) {
//        this.userName = userName
//        this.API_KEY = API_KEY
//        this.token = token
//    }

    constructor(userName: String?, API_KEY: String?, SENDER_ID: String?) {
        this.userName = userName
        this.API_KEY = API_KEY
        this.SENDER_ID = SENDER_ID
        apiService = APIClient.getAPIService()
    }

    constructor(userName: String?, API_KEY: String?, token: String?, SENDER_ID: String?) {
        this.userName = userName
        this.API_KEY = API_KEY
        this.token = token
        this.SENDER_ID = SENDER_ID
        apiService = APIClient.getAPIService()
    }

    init {
        apiService = APIClient.getAPIService()
    }

    fun sendSMS(recipient: String, message: String): MobileSasaResponse {
        var res: MobileSasaResponse? = null
        apiService?.sendSMS(SENDER_ID!!, recipient, message, API_KEY!!, userName!!)!!
            .enqueue(object : Callback<MobileSasaResponse> {
                override fun onResponse(
                    c: Call<MobileSasaResponse>,
                    response: Response<MobileSasaResponse>
                ) {
                    res = response.body()
                }

                override fun onFailure(call: Call<MobileSasaResponse>, t: Throwable) {

                }
            })
        return res!!
    }

    fun sendMultipleSMS(recipients: List<String>, message: String) {
        if (SENDER_ID == null) SENDER_ID = "MOBILESASA"
        var res: MobileSasaResponse? = null
        for (recipient in recipients) {
            apiService?.sendSMS(SENDER_ID!!, recipient, message, API_KEY!!, userName!!)!!
                .enqueue(object : Callback<MobileSasaResponse> {
                    override fun onResponse(
                        c: Call<MobileSasaResponse>,
                        response: Response<MobileSasaResponse>
                    ) {
                        res = response.body()
                    }

                    override fun onFailure(call: Call<MobileSasaResponse>, t: Throwable) {

                    }
                })
        }
    }
}