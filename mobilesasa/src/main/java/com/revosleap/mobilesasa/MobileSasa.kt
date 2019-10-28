package com.revosleap.mobilesasa

import android.content.Context
import com.revosleap.mobilesasa.cache.SMSDao
import com.revosleap.mobilesasa.cache.SMSDatabase
import com.revosleap.mobilesasa.models.MobileSasaResponse
import com.revosleap.mobilesasa.models.SMS
import com.revosleap.mobilesasa.network.Connectivity
import com.revosleap.mobilesasa.network.NetworkChange
import com.revosleap.mobilesasa.requests.APIClient
import com.revosleap.mobilesasa.requests.APIService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.runOnUiThread
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.microedition.khronos.opengles.GL

class MobileSasa : NetworkChange {
    private var userName: String? = null
    private var API_KEY: String? = null
    private var token: String? = null
    private var SENDER_ID: String? = "MOBILESASA"
    private var apiService: APIService? = null
    private var ctx: Context? = null
    private var database: SMSDatabase? = null
    private var smsDao: SMSDao? = null
    private val recipientList = mutableListOf<String>()
    private var currentMessage = ""
    private var caching = false

    constructor(ctx: Context, userName: String, API_KEY: String) {
        this.ctx = ctx
        this.userName = userName
        this.API_KEY = API_KEY
        apiService = APIClient.getAPIService()
    }

//    constructor(userName: String, API_KEY: String, token: String) {
//        this.userName = userName
//        this.API_KEY = API_KEY
//        this.token = token
//    }

    constructor(ctx: Context, userName: String?, API_KEY: String?, SENDER_ID: String?) {
        this.ctx = ctx
        this.userName = userName
        this.API_KEY = API_KEY
        this.SENDER_ID = SENDER_ID
        apiService = APIClient.getAPIService()
    }

    constructor(ctx: Context, userName: String, API_KEY: String, token: String, SENDER_ID: String) {
        this.ctx = ctx
        this.userName = userName
        this.API_KEY = API_KEY
        this.token = token
        this.SENDER_ID = SENDER_ID
        apiService = APIClient.getAPIService()
    }

    init {
        apiService = APIClient.getAPIService()
        database = SMSDatabase.build(ctx!!)
        smsDao = database?.smsDao()
    }

    fun sendSMS(recipient: String, message: String): MobileSasaResponse {
        if (SENDER_ID == null) SENDER_ID = "MOBILESASA"
        var res: MobileSasaResponse? = null
        recipientList.add(recipient)
        currentMessage = message
        if (Connectivity.isConnected(ctx!!)) {
            apiService?.sendSMS(SENDER_ID!!, recipient, message, API_KEY!!, userName!!)!!
                .enqueue(object : Callback<MobileSasaResponse> {
                    override fun onResponse(
                        c: Call<MobileSasaResponse>,
                        response: Response<MobileSasaResponse>
                    ) {
                        res = response.body()
                        recipientList.remove(recipient)
                    }

                    override fun onFailure(call: Call<MobileSasaResponse>, t: Throwable) {

                    }
                })
        } else {
            cacheSMS()
        }
        return res!!
    }

    fun sendSMS(recipients: List<String>, message: String): MutableList<MobileSasaResponse> {
        if (SENDER_ID == null) SENDER_ID = "MOBILESASA"
        recipientList.addAll(recipients)
        currentMessage = message
        val res = mutableListOf<MobileSasaResponse>()
        if (Connectivity.isConnected(ctx!!)) {
            for (recipient in recipients) {
                apiService?.sendSMS(SENDER_ID!!, recipient, message, API_KEY!!, userName!!)!!
                    .enqueue(object : Callback<MobileSasaResponse> {
                        override fun onResponse(
                            c: Call<MobileSasaResponse>,
                            response: Response<MobileSasaResponse>
                        ) {
                            res.add(response.body()!!)
                            recipientList.remove(recipient)
                        }

                        override fun onFailure(call: Call<MobileSasaResponse>, t: Throwable) {

                        }
                    })
            }
        } else {
            cacheSMS()
        }
        return res
    }

    private fun sendSMS(caches: List<SMS>): MutableList<MobileSasaResponse> {
        if (SENDER_ID == null) SENDER_ID = "MOBILESASA"
        val res = mutableListOf<MobileSasaResponse>()
        if (Connectivity.isConnected(ctx!!)) {
            for (s in caches) {
                recipientList.add(s.recipient!!)
                currentMessage = s.message!!
                apiService?.sendSMS(
                    SENDER_ID!!,
                    s.recipient!!,
                    s.message!!,
                    API_KEY!!,
                    userName!!
                )!!
                    .enqueue(object : Callback<MobileSasaResponse> {
                        override fun onResponse(
                            c: Call<MobileSasaResponse>,
                            response: Response<MobileSasaResponse>
                        ) {
                            res.add(response.body()!!)
                            recipientList.remove(s.recipient!!)
                            GlobalScope.launch { smsDao?.deleteOne(s) }
                        }

                        override fun onFailure(call: Call<MobileSasaResponse>, t: Throwable) {

                        }
                    })
            }
        } else {
            cacheSMS()
        }
        return res
    }

    override fun networkChanged(connected: Boolean) {
        when {
            connected -> getCache()
            else -> cacheSMS()
        }
    }

    private fun getCache() {
        if (caching) {

        } else {
            var cachedSMS: MutableList<SMS>
            GlobalScope.launch {
                cachedSMS = smsDao?.getCached()!!
                ctx?.runOnUiThread { sendSMS(cachedSMS) }
            }
        }
    }

    private fun cacheSMS() {
        caching = true
        if (recipientList.isNotEmpty()) {
            for (recipient in recipientList) {
                val sms = SMS(recipient = recipient, message = currentMessage)
                GlobalScope.launch { smsDao?.insert(sms) }
            }
        }
        caching = false
    }
}