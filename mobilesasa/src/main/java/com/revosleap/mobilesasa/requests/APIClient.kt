package com.revosleap.mobilesasa.requests

object APIClient {
//    const val BASE_URL = "https://account.mobilesasa.com/api/express-post?senderID=\"\n" +
//            "                . $senderId\n" +
//            "                . \"&phone=\" . $recipient\n" +
//            "                . \"&message=\" . $message\n" +
//            "                . \"&api_key=\" . $apiKey\n" +
//            "                . \"&username=\" . $username"

    private const val BASE_URL = "https://account.mobilesasa.com/api/"
    fun getAPIService(): APIService =
        RetrofitClient.getClient(baseURL = BASE_URL)!!.create(APIService::class.java)
}