package com.revosleap.mobilesasa.models

data class MobileSasaResponse(
    var code: Int? = null,
    var messageID: List<String>? = null,
    var status: String? = null,
    var smsCost: Double? = null
)