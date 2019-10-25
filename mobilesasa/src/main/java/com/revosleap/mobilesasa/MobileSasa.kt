package com.revosleap.mobilesasa

import com.revosleap.mobilesasa.requests.APIClient
import com.revosleap.mobilesasa.requests.APIService

class MobileSasa {
    private var userName: String? = null
    private var API_KEY: String? = null
    private var token: String? = null
    private var SENDER_ID: String? = "MOBILESASA"

    private var apiService: APIService? = null

    private val BASE_URL = "https://account.mobilesasa.com/api/express-post?senderID="
    //            "                . $senderId\n" +
//            "                . \"&phone=\" . $recipient\n" +
//            "                . \"&message=\" . $message\n" +
//            "                . \"&api_key=\" . $apiKey\n" +
//            "                . \"&username=\" . $username"
    private var URL = ""

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

    fun prepareURL(){

    }

    fun sendSMS(recipient: String, message: String) {
        apiService?.sendSMS()
    }

    fun sendMultipleSMS(recipients: List<String>, message: String) {

    }
}