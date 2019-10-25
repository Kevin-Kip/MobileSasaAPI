package com.revosleap.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.revosleap.mobilesasa.MobileSasa
import com.revosleap.mobilesasa.models.MobileSasaResponse

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val m = MobileSasa(
            userName = "KevinKiprotich",
            API_KEY = "$2y$10$/kANDDM8dKMAIJJiPMM.vu.kY1gLO4MKni7lrmx4wteweyY54aHNi"
        )
//        m.sendSMS("0725778511","Hello. Library test")
//        m.sendMultipleSMS(listOf("0725778511","0724963950"),"Hello. Android Library test")
    }
}
